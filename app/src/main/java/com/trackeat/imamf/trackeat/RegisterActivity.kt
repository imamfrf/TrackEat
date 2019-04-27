package com.trackeat.imamf.trackeat

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.lmntrx.android.library.livin.missme.ProgressDialog
import com.trackeat.imamf.trackeat.extension.toast
import com.trackeat.imamf.trackeat.model.User
import com.trackeat.imamf.trackeat.util.Constant
import com.trackeat.imamf.trackeat.util.Constant.DEFAULT.DEFAULT_NOT_SET
import com.trackeat.imamf.trackeat.util.PreferenceHelper
import com.trackeat.imamf.trackeat.util.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
//    private lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mDatabaseReference = FirebaseDatabase.getInstance().reference.child(Constant.CHILD.CHILD_USERS)
        mFirebaseAuth = FirebaseAuth.getInstance()

        buttonRegister.setOnClickListener {
            validateInput()
        }

        textViewLogin.setOnClickListener {
            if (mFirebaseAuth.currentUser != null)
                FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    // Check input field and register if valid
    private fun validateInput() {
        val email = editTextEmail.text.toString().trim()
        val nama = editTextNamaLengkap.text.toString().trim()
        val tanggalLahir = editTextTanggalLahir.text.toString().trim()
        val tinggiBadan = editTextTinggiBadan.text.toString().trim()
        val beratBadan = editTextBeratBadan.text.toString().trim()
        val password = editTextPassword.text.toString().trim()
        val confirmPassword = editTextKonfirmasiPassword.text.toString().trim()
        val checkedRadioButtonId = radioGroupJenisKelamin.checkedRadioButtonId
        val jenisKelaminRadioButton = findViewById<RadioButton>(checkedRadioButtonId)
        val jenisKelamin = jenisKelaminRadioButton.text.toString().trim()
        if (inputDataNotEmpty(email, nama, password, confirmPassword, tanggalLahir, tinggiBadan, beratBadan, jenisKelamin)) {
            if (password.equals(confirmPassword)) {
                registerUser(email, nama, password, tanggalLahir, tinggiBadan, beratBadan, jenisKelamin)
            } else {
                toast("Kata sandi tidak cocok")
            }
        } else {
            toast("Data tidak lengkap")
        }
    }

    // Register user to firebase
    private fun registerUser(email: String, nama: String, password: String, tanggalLahir: String, tinggiBadan: String, beratBadan: String, jenisKelamin: String) {
        startLoadingIndicator()
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                toast("Register berhasil")
                val userId = mFirebaseAuth.currentUser!!.uid
                val photoUrl = DEFAULT_NOT_SET
                val newUser = User(userId, nama, email, tanggalLahir, jenisKelamin, tinggiBadan, beratBadan, photoUrl)
                // send user data to firebase database
                mDatabaseReference.child(userId).setValue(newUser).addOnCompleteListener {
                    stopLoadingIndicator()
                    val sharedPreferences = PreferenceHelper.defaultPrefs(this)
                    sharedPreferences["nama"] = nama
                    sharedPreferences["email"] = email
                    sharedPreferences["photoUrl"] = photoUrl
                    sendEmailVerification()
                    FirebaseAuth.getInstance().signOut()
                }
            } else {
                stopLoadingIndicator()
                val errorCode = (task.exception as FirebaseAuthException).errorCode
                when (errorCode) {
//                    "ERROR_WEAK_PASSWORD" ->
//                        toast("Password harus memiliki minimal 6 karakter")
                    "ERROR_EMAIL_ALREADY_IN_USE" ->
                        toast("Alamat email sudah digunakan")
                    else ->
                        toast("" + (task.exception as FirebaseAuthException).message)
                }
            }
        }
    }

    // Send email verification to user
    private fun sendEmailVerification() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val snackbar = Snackbar
                        .make(constraintLayout, "Email verifikasi dikirim ke ${user.email}", Snackbar.LENGTH_LONG)
                        .setAction("BUKA EMAIL") {
                            val intent = Intent(Intent.ACTION_MAIN)
                            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                            startActivity(Intent.createChooser(intent, "Choose email client"))
                        }
                snackbar.show()
            } else {
                Toast.makeText(baseContext,
                        "Gagal mengirim email verifikasi",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inputDataNotEmpty(email: String, nama: String, password: String, confirmPassword: String, tanggalLahir: String, tinggiBadan: String, beratBadan: String, jenisKelamin: String): Boolean {
        return !(TextUtils.isEmpty(email) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) ||
                TextUtils.isEmpty(tanggalLahir) || TextUtils.isEmpty(tinggiBadan) || TextUtils.isEmpty(beratBadan) || TextUtils.isEmpty(jenisKelamin))
    }

    private fun startLoadingIndicator() {
        viewDimScreen.visibility = View.VISIBLE
        aviLoadingIndicatorView.smoothToShow()
    }

    private fun stopLoadingIndicator() {
        viewDimScreen.visibility = View.GONE
        aviLoadingIndicatorView.smoothToHide()
    }
}
