package com.dkb.gravitygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dkb.gravitygame.databinding.FragmentMainMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mainMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentMainMenuBinding
    private lateinit var navController : NavController
    private lateinit var gameViewModel: GameViewModel
    private var gameModel: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        gameModel = gameViewModel.gameLiveModel.value
        binding.buttonPlayLevelEarth.setOnClickListener {
            if (gameModel!=null)
            {
                gameModel!!.level = 1
            }
            navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }
        binding.buttonPlayLevelMoon.setOnClickListener {
            if (gameModel!=null)
            {
                gameModel!!.level = 2
            }
            navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }
        binding.buttonPlayLevelMars.setOnClickListener {
            if (gameModel!=null)
            {
                gameModel!!.level = 3
            }
            navController.navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }

        if (gameModel != null)
        {
            if (gameModel!!.level1HS == -1) {
                binding.textEarthHighscoreCount.text = "N/A"
            } else {
                binding.textEarthHighscoreCount.text = gameModel!!.level1HS.toString()
            }
            if (gameModel!!.level2HS == -1) {
                binding.textMoonHighscoreCount.text = "N/A"
            } else {
                binding.textMoonHighscoreCount.text = gameModel!!.level2HS.toString()
            }
            if (gameModel!!.level3HS == -1) {
                binding.textMarsHighscoreCount.text = "N/A"
            } else {
                binding.textMarsHighscoreCount.text = gameModel!!.level3HS.toString()
            }

            binding.buttonPlayLevelMoon.setEnabled(false)
            binding.buttonPlayLevelMars.setEnabled(false)
            if (gameModel!!.level1HS >= 15) {
                binding.buttonPlayLevelMoon.setEnabled(true)
            }
            if (gameModel!!.level2HS >= 15) {
                binding.buttonPlayLevelMars.setEnabled(true)
            }
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mainMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            mainMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}