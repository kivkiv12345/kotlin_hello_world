package com.example.helloworld

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.helloworld.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    companion object {  // First fragment toast button info
        private const val initial_toast = "Hello Toast!"
        var toast_text = initial_toast
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->

            // Input text dialog stolen from https://stackoverflow.com/questions/10903754/input-text-dialog-android
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Change toast text")

            // Set up the input
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK", { dialog, which -> toast_text = input.text.toString() })
            builder.setNegativeButton("Cancel", { dialog, which -> dialog.cancel() })
            builder.setNeutralButton("Reset", { dialog, which -> toast_text = initial_toast })

            // Get object to open and close the keyboard for this alert dialog,
            // as that apparently doesn't happen automatically
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            builder.setOnCancelListener({ dialog -> imm.hideSoftInputFromWindow(view.windowToken, 0) })

            builder.show()

            // Show keyboard when the alert dialog is opened
            // Stolen from https://dev.to/collinskesuibai/show-hide-android-soft-keyboard-android-31-seconds-of-code-1297
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//            imm.showSoftInput(view, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}