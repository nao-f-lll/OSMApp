package com.project.osmapp.logic
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

object AuthUtils {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerWithEmail(email: String, password: String, onComplete: (result: Task<AuthResult>?, errorMessage: String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Registro exitoso
                    onComplete(task, null)
                } else {
                    // Manejo del error
                    onComplete(null, task.exception?.localizedMessage)
                }
            }
    }

    fun loginWithEmail(email: String, password: String, onComplete: (result: Task<AuthResult>?, errorMessage: String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    onComplete(task, null)
                } else {
                    // Manejo del error
                    onComplete(null, task.exception?.localizedMessage)
                }
            }
    }
    fun signInWithGoogle(idToken: String, onComplete: (result: Task<AuthResult>?, errorMessage: String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(task, null)
                } else {
                    onComplete(null, task.exception?.localizedMessage)
                }
            }
    }


    fun getIdToken(account: GoogleSignInAccount): String? {
        return account.idToken
    }
    // Obtener el usuario actual (si ha iniciado sesión)
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    // Cerrar sesión
    fun signOut() {
        auth.signOut()
        FirebaseAuth.getInstance().signOut()
    }

}