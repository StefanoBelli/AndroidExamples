package it.ste.guessanumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import it.ste.guessanumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var game: Game
    private lateinit var numberBtns: Array<Button>
    private var started = false
    private var numberOfRetries = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) {
            game = savedInstanceState.get("game") as Game
            started = savedInstanceState.get("started") as Boolean
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        initHandlers()

        game = Game()
    }

    private fun initHandlers() {
        binding.cBtn.setOnClickListener(CancelButtonClickHandler())
        binding.okBtn.setOnClickListener(OkButtonClickHandler())
        binding.statusIv.setOnLongClickListener(StatusImageLongClickHandler())

        numberBtns = arrayOf(
            binding.zeroBtn,
            binding.oneBtn,
            binding.twoBtn,
            binding.threeBtn,
            binding.fourBtn,
            binding.fiveBtn,
            binding.sixBtn,
            binding.sevenBtn,
            binding.eightBtn,
            binding.nineBtn)

        numberBtns.forEach { it.setOnClickListener(NumberButtonClickListener()) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("game", game)
        outState.putBoolean("started", started)
    }

    private fun showShortToast(stringId: Int) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_SHORT).show()
    }

    private fun toggleOkAndCBtns() {
        if (binding.inputTv.text.isEmpty()) {
            binding.cBtn.isEnabled = false
            binding.okBtn.isEnabled = false
        } else {
            binding.cBtn.isEnabled = true
            binding.okBtn.isEnabled = true
        }
    }

    private inner class OkButtonClickHandler : View.OnClickListener {
        override fun onClick(v: View?) {
            if(binding.inputTv.length() > 0) {
                val res = game.check(binding.inputTv.text.toString().toInt())
                if(res == Game.Status.WIN) {
                    numberOfRetries = 0
                    binding.gameStatusTv.text = getText(R.string.start_game_youwin)
                } else if(res == Game.Status.LOOSE) {
                    numberOfRetries = 0
                    binding.gameStatusTv.text = getText(R.string.start_game_youloose)
                } else if(res == Game.Status.TOO_BIG) {
                    numberOfRetries++
                    binding.gameStatusTv.text = getText(R.string.toobig)
                } else if(res == Game.Status.TOO_SMALL) {
                    numberOfRetries++
                    binding.gameStatusTv.text = getText(R.string.toosmall)
                }
            }
        }
    }

    private inner class CancelButtonClickHandler : View.OnClickListener {
        override fun onClick(v: View?) {
            if(binding.inputTv.length() > 0) {
                binding.inputTv.text = binding.inputTv.text.subSequence(0, binding.inputTv.length())
            }
            toggleOkAndCBtns()
        }
    }

    private inner class NumberButtonClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if(binding.inputTv.length() == R.integer.max_input_len) {
                showShortToast(R.string.max_number)
            } else {
                v as Button
                binding.inputTv.text = "${binding.inputTv.text}${v.text}"
                toggleOkAndCBtns()
            }
        }
    }

    private inner class StatusImageLongClickHandler : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            if(!started) {
                numberBtns.forEach { it.isEnabled = true }
                started = true
            } else {
               showShortToast(R.string.game_already_started)
            }

            return true
        }
    }
}