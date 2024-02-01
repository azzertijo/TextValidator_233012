package com.example.textvalidator_233012

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.example.textvalidator_233012.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()
        userFocusListener()
        binding.submitButton.setOnClickListener{ submitForm()}

    }

    private fun submitForm()
    {
        binding.emailContainer.helperText = validEmail()
        binding.userContainer.helperText = validUser()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()

        val validEmail = binding.emailContainer.helperText == null
        val validUser = binding.userContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail && validPassword && validPhone && validUser)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm()
    {
        var message = ""
        if(binding.emailContainer.helperText != null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.userContainer.helperText != null)
            message += "\n\nUsuario: " + binding.userContainer.helperText
        if(binding.passwordContainer.helperText != null)
            message += "\n\nContraseña: " + binding.passwordContainer.helperText
        if(binding.phoneContainer.helperText != null)
            message += "\n\nNúmero: " + binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Formulario invalido")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm()
    {
        var message = "Email: " + binding.emailEditText.text
        message += "\nUsuario: " + binding.userEditText.text
        message += "\nContraseña: " + binding.passwordEditText.text
        message += "\nNúmero: " + binding.phoneEditText.text

        AlertDialog.Builder(this)
            .setTitle("Formulario enviado")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null
                binding.userEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
                binding.userContainer.helperText = getString(R.string.required)
            }
            .show()
    }

    private fun emailFocusListener(){
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String?
    {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Email inválido"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8)
        {
            return "Mínimo 8 cáracteres"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Debe de contener 1 letra mayúscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Debe de contener 1 letra minuscula"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Debe de contener una caracter especial (@#\$%^&+=)"
        }

        return null
    }

    private fun phoneFocusListener()
    {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.phoneEditText.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Solo deben de ser dígitos"
        }
        if(phoneText.length != 10)
        {
            return "Deben de ser 10 dígitos"
        }
        return null
    }

    private fun userFocusListener(){
        binding.userEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.userContainer.helperText = validUser()
            }
        }
    }

    private fun validUser(): String?{

        val reservedWords = listOf("admin", "root")
        val userText = binding.userEditText.text.toString()
        if (userText.length < 3 || userText.length > 12) {
            return "Nombre de usuario debe tener entre 3 y 12 caracteres"
        }
        if (reservedWords.contains(userText.lowercase())) {
            return "Nombre de usuario no puede ser una palabra reservada"
        }
        if (!userText.matches("[a-zA-Z0-9_]+".toRegex())) {
            return "Nombre de usuario solo puede contener letras, números y guiones bajos"
        }
        if (!userText.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d_]+".toRegex())) {
            return "Nombre de usuario debe contener al menos una letra y un número"
        }


        return null
    }

}