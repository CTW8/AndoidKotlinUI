package com.example.fragment_learn


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextAccount: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var textViewRegister: TextView

    // 服务器登录接口示例，可根据实际项目修改
    private val loginUrl = "https://example.com/api/login"

    // OkHttpClient 一般建议做成单例，这里示例写在 Activity 内部
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        editTextAccount = findViewById(R.id.editText_account)
        editTextPassword = findViewById(R.id.editText_password)
        buttonLogin = findViewById(R.id.button_login)
        textViewForgotPassword = findViewById(R.id.textView_forgot_password)
        textViewRegister = findViewById(R.id.textView_register)
    }

    private fun setupListeners() {
        // 点击“登录”按钮后发送网络请求
        buttonLogin.setOnClickListener {
            val account = editTextAccount.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请填写账号和密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 调用函数执行网络登录
            loginWithOkHttp(account, password)
        }

        // 点击“忘记密码”
        textViewForgotPassword.setOnClickListener {
            Toast.makeText(this, "忘记密码 - 可跳转到密码找回界面", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // 点击“注册”
        textViewRegister.setOnClickListener {
            Toast.makeText(this, "注册 - 可跳转到注册界面", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    /**
     * 使用 OkHttp 发送登录请求
     * @param account 用户输入的账号
     * @param password 用户输入的密码
     */
    private fun loginWithOkHttp(account: String, password: String) {
        // 根据实际后端接口决定是 JSON 还是表单等方式
        // 这里使用 JSON 示例：
        val jsonBody = JSONObject().apply {
            put("username", account)
            put("password", password)
        }
        val requestBody = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            jsonBody.toString()
        )

        // 构建 POST 请求
        val request = Request.Builder()
            .url(loginUrl)
            .post(requestBody)
            .build()

        // 异步网络请求
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 网络异常或请求失败
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "请求失败，请检查网络！", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 读取服务器返回的响应
                val responseBody = response.body?.string()
                if (!response.isSuccessful || responseBody.isNullOrEmpty()) {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "服务器错误或无响应！", Toast.LENGTH_SHORT).show()
                    }
                    return
                }

                try {
                    val json = JSONObject(responseBody)
                    // 假设后端约定： code=200 表示登录成功；否则提示错误信息
                    val code = json.optInt("code")
                    val msg = json.optString("msg")

                    if (code == 200) {
                        // 登录成功。通常会返回 token 或 userId 等信息
                        val token = json.optString("token", "")
                        // 根据业务逻辑，存储 token
                        saveLoginState(token)

                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                            // 跳转到主界面
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        }
                    } else {
                        // 登录失败，显示后端返回的错误信息
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "数据解析异常", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    /**
     * 将登录状态或 Token 等信息持久化（示例使用 SharedPreferences）
     */
    private fun saveLoginState(token: String) {
        val sharedPrefs = getSharedPreferences("my_app_prefs", MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean("is_logged_in", true)
            putString("user_token", token)
            // 根据需要也可以存储 token 有效期
            // putLong("token_expiry_time", System.currentTimeMillis() + N * 1000)
            apply()
        }
    }
}