package sigma.telkomgroup.controller

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import sigma.telkomgroup.R
import sigma.telkomgroup.connection.ConstantUtil
import sigma.telkomgroup.model.ModelUser
import sigma.telkomgroup.utils.SessionManager


class ControllerChangePassword : AppCompatActivity(), View.OnClickListener {

    lateinit var oldPassword : String
    lateinit var newPassword : String
    lateinit var confirmPassword : String
    lateinit var clChangePass : ConstraintLayout
    lateinit var session : SessionManager
    lateinit var modelUser : ModelUser

//    private val connection: dbConnection? = null

    lateinit var etOldPass : EditText
    lateinit var etNewPass : EditText
    lateinit var etConfirmPass : EditText
    lateinit var progressBar: ProgressBar
    lateinit var btnSubmitChangePass: Button
    lateinit var user : HashMap<String, String>
    lateinit var password : String
    lateinit var user_id : String

    lateinit var oldPassToString : String
    lateinit var newPassToString : String
    lateinit var confPassToString : String

//    tambahin progress dialog
//    lateinit var progressDialogHelper : ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_controller_change_password)

        etOldPass = findViewById(R.id.et_oldpassword)
        etNewPass = findViewById(R.id.et_newpassword)
        etConfirmPass = findViewById(R.id.et_newconfirmpassword)
        btnSubmitChangePass = findViewById(R.id.btn_submit_changepassword)

        AndroidNetworking.initialize(this)
        session = SessionManager(this)
        user = session.userDetails
        btnSubmitChangePass.setOnClickListener(this)
        session = SessionManager(this)

        password = user[ConstantUtil.LOGIN.TAG_PASS].toString()
        print("Check password value at change pass act: $password")
        user_id = user[ConstantUtil.LOGIN.TAG_ID].toString()
        print("check user_id value at change pass act: $user_id")


    }

    private fun showLoading(state: Boolean){
        if (state){
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_submit_changepassword -> {

                oldPassToString = etOldPass.text.toString()
                newPassToString = etNewPass.text.toString()
                confPassToString = etConfirmPass.text.toString()


                if (oldPassToString == "") {

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please input old password first")
                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    builder.show()

                } else if (newPassToString == "") {

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please input new password first")
                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    builder.show()

                } else if (confPassToString == "") {

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Please input confirmation password first")
                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    builder.show()

                } else if (oldPassToString != password) {

                    Log.d("password salah", "old password salah")

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Wrong password")
                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    builder.show()

                } else if (newPassToString != confPassToString) {

                    print("Password tidak match")
                    Log.d("password salah", " Password salah")

                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Password confirmation does not match")
                    builder.setPositiveButton("OK") { dialog, which ->

                    }
                    builder.show()

                } else {

                    Log.d("password salah", "change password berhasil")

                    postChangePass(newPassToString)

                }

            }
        }
    }

    private fun postChangePass(newPass: String){

//        showLoading(true)
        Log.d("Post Change Pass", "masuk Post Change Password")
        Log.d("Check body post", "Check body post change password: $user_id  & $newPass")
        val urlChangePass = ConstantUtil.URL.SERVER + "api_data/updatePassword"
        Log.d("test url", "Url for change password : $urlChangePass")

        AndroidNetworking.post(urlChangePass)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("new_password", newPass)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {

//                        showLoading(false)
                        Log.d("Onresponse", "Onresponse change pass: $response")
                        print("response post change pass : $response")

                        if (response?.getString("status") == "1") {

                            val builder = AlertDialog.Builder(this@ControllerChangePassword)
                            builder.setMessage("Successfully change password")
                            builder.setPositiveButton("OK") { dialog, which ->
                                logout()
                                session.updatePassword(newPassToString)
                            }
                            builder.show()
                        }
                    }

                    override fun onError(anError: ANError?) {
//                        showLoading(false)
                        Log.d("OnError", "OnError change pass: $anError")
                        var error = anError.toString()
                        Toast.makeText(this@ControllerChangePassword, "$error", Toast.LENGTH_LONG).show()
                    }
                })

    }

    private fun logout(){
        session.logoutUser()
    }
}