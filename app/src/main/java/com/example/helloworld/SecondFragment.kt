package com.example.helloworld

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.helloworld.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun phoneNumberInput(view: View) {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(view.context)
        builder.setTitle("Phone Number Input")

        // Set up the input
        val input = EditText(view.context)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Enter phone number")
        input.inputType = InputType.TYPE_CLASS_PHONE
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            var phone_number: String = input.text.toString()

            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_number))
            activity!!.startActivity(callIntent)
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private val args: SecondFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val count = args.myArg
        val countText = getString(R.string.random_heading, count)
        view.findViewById<TextView>(R.id.textview_header).text = countText

        view.findViewById<Button>(R.id.button_previous).setOnClickListener {
            val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment()
            action.initialCount = count
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.call_button).setOnClickListener {
//            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
//            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "41137018"))
//            activity!!.startActivity(callIntent)
            phoneNumberInput(view)
        }

        val random = java.util.Random()
        var randomNumber = 0
        if (count > 0) {
            randomNumber = random.nextInt(count + 1)
        }
        if (randomNumber == 0) {
            // Display sink
        }
        view.findViewById<TextView>(R.id.textview_random).text = randomNumber.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}