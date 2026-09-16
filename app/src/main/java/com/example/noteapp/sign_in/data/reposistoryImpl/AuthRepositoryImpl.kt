package com.example.noteapp.sign_in.data.reposistoryImpl

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.noteapp.R
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : AuthReposistory {

    private var user = auth.currentUser

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override fun isUserisCurrentlyloggedIN(): Boolean {
        Log.d("AuthRepositoryImpl", "isUserisCurrentlyloggedIN called  ${user?.uid} ${user?.displayName}  ${user?.email}")
        return user != null

    }

    override suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val credentialManager = CredentialManager.create(context)
            
            // Note: Make sure default_web_client_id is available in strings.xml (usually from google-services.json)
            val webClientId = context.getString(R.string.default_web_client_id)
            
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val resultedcredential = result.credential
            
            if (resultedcredential is CustomCredential && resultedcredential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(resultedcredential.data)
                
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                val authResult = auth.signInWithCredential(firebaseCredential).await()
                
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    user = firebaseUser
                    Result.success(firebaseUser)
                } else {
                    Result.failure(Exception("Firebase user is null after sign in"))
                }
            } else {
                Result.failure(Exception("Unexpected credential type: ${resultedcredential.type}"))
            }
        } catch (e: GetCredentialException) {
            Log.e("AuthRepositoryImpl", "Credential Manager failed: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Google Sign In failed: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}