package com.example.mangatronmobile.ui.fragment

import BestSellersAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangatronmobile.R
import com.example.mangatronmobile.adapter.TopItemsAdapter
import com.example.mangatronmobile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var  binding: FragmentHomeBinding

    lateinit var recyclerView: RecyclerView

    private var bestImageList = ArrayList<Int>()
    private var bestNameList = ArrayList<String>()
    private var bestPriceList = ArrayList<String>()
    private lateinit var bestAdapter: BestSellersAdapter

    private var topImageList = ArrayList<Int>()
    private var topNameList = ArrayList<String>()
    private var topPriceList = ArrayList<String>()
    private lateinit var topAdapter: TopItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        bestImageList.add(R.drawable.akatsuki)
        bestImageList.add(R.drawable.ls)
        bestImageList.add(R.drawable.vol1)



        bestNameList.add("Akatsuki Ring")
        bestNameList.add("Luffy Figurine")
        bestNameList.add("One Piece vol.1")




        bestPriceList.add("$ 1,500")
        bestPriceList.add("$ 1,000")
        bestPriceList.add("$ 1,900")



        bestAdapter = BestSellersAdapter(
            requireContext(),
            bestImageList,
            bestNameList,
            bestPriceList
        )

        binding.bestRecycler.adapter = bestAdapter
        binding.bestRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)




        topImageList.add(R.drawable.enma)
        topImageList.add(R.drawable.bounty)
        topImageList.add(R.drawable.hoodie)



        topNameList.add("Zoro's Enma")
        topNameList.add("Bounty Posters")
        topNameList.add("Hoodie")




        topPriceList.add("$ 8,000")
        topPriceList.add("$ 1,000")
        topPriceList.add("$ 2,000")



        topAdapter = TopItemsAdapter(
            requireContext(),
            topImageList,
            topNameList,
            topPriceList
        )

        binding.topRecycler.adapter = topAdapter
        binding.topRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    }

