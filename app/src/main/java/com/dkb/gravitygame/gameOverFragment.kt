package com.dkb.gravitygame

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dkb.gravitygame.databinding.FragmentGameOverBinding
import com.dkb.gravitygame.databinding.FragmentMainMenuBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [gameOverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class gameOverFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentGameOverBinding
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.buttonMainMenu.setOnClickListener {
            navController.navigate(R.id.action_gameOverFragment_to_mainMenuFragment)
        }

        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        gameModel = gameViewModel.gameLiveModel.value

        if (gameModel!=null)
        {
            when (gameModel!!.level) {
                1 -> {
                    binding.frameLayout2.background = requireContext().resources.getDrawable(R.drawable.earth, null)
                    if (gameModel!!.lastScore > gameModel!!.level1HS)
                    {
                        gameModel!!.level1HS = gameModel!!.lastScore
                    }
                }
                2 -> {
                    binding.frameLayout2.background = requireContext().resources.getDrawable(R.drawable.moon, null)
                    if (gameModel!!.lastScore > gameModel!!.level2HS)
                    {
                        gameModel!!.level2HS = gameModel!!.lastScore
                    }
                }
                3 -> {
                    binding.frameLayout2.background = requireContext().resources.getDrawable(R.drawable.mars, null)
                    if (gameModel!!.lastScore > gameModel!!.level3HS)
                    {
                        gameModel!!.level3HS = gameModel!!.lastScore
                    }
                }
            }
        }

        gameViewModel.gameLiveModel.observe(viewLifecycleOwner, Observer {
            if (gameModel != null)
            {
                binding.textFinalScore.text = gameModel!!.lastScore.toString()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment gameOverFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            gameOverFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}