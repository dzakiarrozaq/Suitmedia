package com.bangkit.suitmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bangkit.suitmedia.databinding.ActivityMainBinding
import com.bangkit.suitmedia.ui.ListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_NAME)
        var selectedUser = intent.getStringExtra(SELECTED)

        binding.tvUsername.text = username

        if (selectedUser.isNullOrEmpty()) {
            selectedUser = getString(R.string.selected_user_name)
        }

        binding.tvSelectedUser.text = selectedUser

        setupAction()
    }

    private fun setupAction() {
        binding.bottomButton.setOnClickListener {
            val listActivityIntent = Intent(this, ListActivity::class.java)
            startActivityForResult(listActivityIntent, LIST_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedUser = data?.getStringExtra(MainActivity.SELECTED)
            binding.tvSelectedUser.text = selectedUser
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val SELECTED = "Selected User Name"
        private const val LIST_ACTIVITY_REQUEST_CODE = 1
    }
}