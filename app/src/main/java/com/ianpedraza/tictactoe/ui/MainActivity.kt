package com.ianpedraza.tictactoe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ianpedraza.tictactoe.databinding.ActivityMainBinding
import com.ianpedraza.tictactoe.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: GameViewModel by viewModels()

    private val dialog by lazy {
        AlertDialog.Builder(this)
            .setTitle("Reset Game")
            .setMessage("Are you sure you want to reset the game? all data will be lost.")
            .setPositiveButton("Reset") { _, _ -> viewModel.reset() }
            .setNegativeButton("Cancel", null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        subscribeObservers()
    }

    private fun setupUI() {
        binding.apply {
            btnReset.setOnClickListener { dialog.show() }
            iv00.setOnClickListener { viewModel.onCell00Pressed() }
            iv01.setOnClickListener { viewModel.onCell01Pressed() }
            iv02.setOnClickListener { viewModel.onCell02Pressed() }
            iv10.setOnClickListener { viewModel.onCell10Pressed() }
            iv11.setOnClickListener { viewModel.onCell11Pressed() }
            iv12.setOnClickListener { viewModel.onCell12Pressed() }
            iv20.setOnClickListener { viewModel.onCell20Pressed() }
            iv21.setOnClickListener { viewModel.onCell21Pressed() }
            iv22.setOnClickListener { viewModel.onCell22Pressed() }
        }
    }

    private fun subscribeObservers() {
        viewModel.apply {
            cell00.observe(this@MainActivity) { changeResource(binding.iv00, it) }
            cell01.observe(this@MainActivity) { changeResource(binding.iv01, it) }
            cell02.observe(this@MainActivity) { changeResource(binding.iv02, it) }
            cell10.observe(this@MainActivity) { changeResource(binding.iv10, it) }
            cell11.observe(this@MainActivity) { changeResource(binding.iv11, it) }
            cell12.observe(this@MainActivity) { changeResource(binding.iv12, it) }
            cell20.observe(this@MainActivity) { changeResource(binding.iv20, it) }
            cell21.observe(this@MainActivity) { changeResource(binding.iv21, it) }
            cell22.observe(this@MainActivity) { changeResource(binding.iv22, it) }
            turn.observe(this@MainActivity) { changeResource(binding.ivTurn, it) }
            winnerMessage.observe(this@MainActivity) {
                it?.let {
                    showWinner(it)
                    viewModel.onWinnerShowed()
                }
            }
        }
    }

    private fun changeResource(imageView: ImageView, res: Int?) {
        imageView.setImageResource(res ?: GameViewModel.EMPTY_IMAGE)
    }

    private fun showWinner(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Accept", null)
            .setOnDismissListener {
                viewModel.reset()
            }
            .show()
    }
}