package jp.ac.it_college.std.s22012.navigationsample.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s22012.navigationsample.databinding.FragmentThreedBinding


class ThreadFragment : Fragment() {
    private var _binding: FragmentThreedBinding? = null
    private val binding get() = _binding!!
    private val args: ThreadFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThreedBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val choice = arguments?.getInt("choice", 0)
//        binding.Display.text = ("$choice")
          binding.Display.text = "<${args.choice}>"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}