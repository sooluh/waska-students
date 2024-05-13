package id.my.suluh.waska.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.my.suluh.waska.databinding.FragmentSubmitBinding
import id.my.suluh.waska.viewmodel.SubmitViewModel

class SubmitFragment : Fragment() {

    private var _binding: FragmentSubmitBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val submitViewModel = ViewModelProvider(this).get(SubmitViewModel::class.java)

        _binding = FragmentSubmitBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val textView: TextView = binding.textNotifications

        submitViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
