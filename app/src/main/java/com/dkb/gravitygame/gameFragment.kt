package com.dkb.gravitygame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.dkb.gravitygame.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [gameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class gameFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentGameBinding
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
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        gameModel = gameViewModel.gameLiveModel.value

        if (gameModel!=null)
        {
            binding.viewGame.level = gameModel!!.level
        }

        binding.viewGame.lifeListener.observe(viewLifecycleOwner, Observer {
            binding.textLivesCounter.text = binding.viewGame.player.lives.toString()
            if (binding.viewGame.player.lives == 0)
            {
                binding.viewGame.isRunning = false
                if (gameModel!=null)
                {
                    gameModel!!.lastScore = binding.viewGame.player.coins
                }
                navController.navigate(R.id.action_gameFragment_to_gameOverFragment)
            }
        })

        binding.viewGame.coinListener.observe(viewLifecycleOwner, Observer {
            binding.textScore.text = binding.viewGame.player.coins.toString()
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment gameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            gameFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}