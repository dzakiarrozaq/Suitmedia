package com.bangkit.suitmedia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bangkit.suitmedia.MainActivity
import com.bangkit.suitmedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }
    private fun setupAction() {
        binding.btncheck.setOnClickListener {
            val palindrome = binding.palindromeEditText.text?.toString()

            if (palindrome.isNullOrEmpty()) {
                binding.palindromeEditText.error = "Must be filled!"
                return@setOnClickListener
            }

            val isPalindrome = isPalindrome(palindrome)

            if (isPalindrome) {
                Toast.makeText(this, "isPalindrome", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnnext.setOnClickListener {
            val name = binding.passwordEditText.text?.toString()

            if (name.isNullOrEmpty()) {
                binding.tvname.error = "Must be filled!"
                return@setOnClickListener
            }

            val palindrome = binding.palindromeEditText.text?.toString()

            if (palindrome.isNullOrEmpty()) {
                binding.palindromeEditText.error = "Must be filled!"
                return@setOnClickListener
            }

            val isPalindrome = isPalindrome(palindrome)

            if (isPalindrome) {
                val moveToSecond = Intent(this, MainActivity::class.java)
                moveToSecond.putExtra(MainActivity.EXTRA_NAME, name)
                startActivity(moveToSecond)
            } else {
                Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isPalindrome(str: String): Boolean {
        var left = 0
        var right = str.length - 1

        while (left < right) {
            if (str[left] != str[right]) {
                return false
            }
            left++
            right--
        }
        return true
    }

    companion object {
        private var TAG = "MainActivity"
    }
}