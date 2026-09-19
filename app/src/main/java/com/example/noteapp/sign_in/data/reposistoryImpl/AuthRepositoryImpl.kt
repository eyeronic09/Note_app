package com.example.noteapp.sign_in.data.reposistoryImpl

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.noteapp.R
import com.example.noteapp.sign_in.data.mapper.Authmapper.toUserData
import com.example.noteapp.sign_in.domain.model.UserData
import com.example.noteapp.sign_in.domain.reposistory.AuthReposistory
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : AuthReposistory {

    private var user = auth.currentUser

    override fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun getCurrentUserData(): UserData? {
        return auth.currentUser?.toUserData()
    }


    override fun isUserisCurrentlyloggedIN(): Boolean {
        Log.d("AuthRepositoryImpl", "isUserisCurrentlyloggedIN called  ${user?.uid} ${user?.displayName}  ${user?.email}")
        return user != null
    }

    override suspend fun signInWithGoogle(context: Context): Result<FirebaseUser> {
        return try {
            val credentialManager = CredentialManager.create(context)
            
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

            val `resulted-credential` = result.credential
            
            if (`resulted-credential` is CustomCredential && `resulted-credential`.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(`resulted-credential`.data)
                
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
                Result.failure(Exception("Unexpected credential type: ${`resulted-credential`.type}"))
            }
        } catch (e: GetCredentialException) {
            Log.e("AuthRepositoryImpl", "Credential Manager failed: ${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Google Sign In failed: ${e.message}")
            Result.failure(e)
        }
        catch (collisionException: FirebaseAuthUserCollisionException) {
            Log.d("AuthRepositoryImpl", "Account collision: email ${collisionException.email} is registered via Google/other provider")
            Result.failure(Exception("An account already exists with ${collisionException.email} using Google Sign-In. Please sign in with Google."))
        }
    }

    override suspend fun signInOrSignUpEmailAndPassword(email: String, password: String): Result<FirebaseUser> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            if (firebaseUser != null) {
                user = firebaseUser
                Result.success(firebaseUser)
            } else {
                Result.failure(Exception("Firebase user is null after sign in"))
            }
        } catch (e: Exception) {
            // If sign in fails, attempt to create user (Sign Up)
            try {
                val createResult = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = createResult.user
                if (firebaseUser != null) {
                    user = firebaseUser
                    Result.success(firebaseUser)
                } else {
                    Result.failure(Exception("Firebase user is null after sign up"))
                }
            } catch (collisionException: FirebaseAuthUserCollisionException) {
                Log.d("AuthRepositoryImpl", "Account collision: email ${collisionException.email} is registered via Google/other provider")
                Result.failure(Exception("An account already exists with ${collisionException.email }email using Google Sign-In. Please sign in with Google."))
            } catch (signUpException: Exception) {
                Log.d("AuthRepositoryImpl", "Sign in / Sign up failed: ${signUpException.message}")
                Result.failure(signUpException)
            }
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}
