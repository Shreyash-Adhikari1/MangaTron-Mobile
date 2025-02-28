package com.example.mangatronmobile.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.example.mangatronmobile.R
import com.example.mangatronmobile.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mangaCard.setOnClickListener {
            replaceFragment(MangaFragment())
        }

        binding.merchCard.setOnClickListener {
            replaceFragment(MerchFragment())
        }

        binding.figurineCard.setOnClickListener {
            replaceFragment(FigurineFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.mainFrame, fragment)
            addToBackStack(null) // Allows user to go back to the origin Fragment , Here it is Category Fragment
        }
    }
}
