package com.example.chapter4_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "High and Low" //タグはログ出力で使用

    //自分と相手のカードの目を保存する変数
    private var yourCard = 0
    private var droidCard = 0

    //あたりの数とはずれの数を加算して保存する変数
    private var hitCount = 0
    private var loseCount = 0

    private var gameStarted = false //ゲームを開始したかどうか
    private var answered = false //出された問題に答えたかどうか

    override fun onCreate(savedInstanceState: Bundle?) {
        //アクティビティの生成時に一度だけ呼出される。初期化処理を行う
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        highBtn.setOnClickListener {
            if ((gameStarted && !answered)) {
                //ゲーム開始状態かつ未回答であればhighAndLowメソッドを実行する
                highAndLow('H')
            }
        }
        lowBtn.setOnClickListener {
            if ((gameStarted && !answered)) {
                highAndLow('L')
            }
        }

        nextBtn.setOnClickListener {
            //「次へ」が押されたら新しいカードをめくる
            if (gameStarted) {
                drawCard()
            }
        }
    }

    override fun onResume() {
        //ユーザーの処理を受け付ける直前に呼出される(=ゲーム開始時にすること)
        super.onResume()
        hitCount = 0
        loseCount = 0
        hitText.text = getString(R.string.hit_text)
        loseText.text = getString(R.string.lose_text)
        gameStarted = true
        drawCard()
    }

    private fun drawCard(){
        //自分も相手も裏向きのカードを表示する
        yourCardImage.setImageResource(R.drawable.z02)
        droidCardImage.setImageResource(R.drawable.z01)

        yourCard = (1..13).random() //乱数生成
        Log.d(tag, "You:"+yourCard)

        when (yourCard) {
            //yourCardに保存された数によって表示するカードが決まる
            1 -> yourCardImage.setImageResource(R.drawable.d01)
            2 -> yourCardImage.setImageResource(R.drawable.d02)
            3 -> yourCardImage.setImageResource(R.drawable.d03)
            4 -> yourCardImage.setImageResource(R.drawable.d04)
            5 -> yourCardImage.setImageResource(R.drawable.d05)
            6 -> yourCardImage.setImageResource(R.drawable.d06)
            7 -> yourCardImage.setImageResource(R.drawable.d07)
            8 -> yourCardImage.setImageResource(R.drawable.d08)
            9 -> yourCardImage.setImageResource(R.drawable.d09)
            10 -> yourCardImage.setImageResource(R.drawable.d10)
            11 -> yourCardImage.setImageResource(R.drawable.d11)
            12 -> yourCardImage.setImageResource(R.drawable.d12)
            13 -> yourCardImage.setImageResource(R.drawable.d13)
        }

        droidCard = (1..13).random() //乱数生成
        Log.d(tag, "droid:"+droidCard)

        answered = false
        //自分のカードが決まった時点ではHIGH/LOWは未回答
    }

    private fun highAndLow(answer: Char) {
        showDroidCard()
        answered = true

        val balance = droidCard - yourCard

        if (balance == 0) {
            //自分のカードと相手のカードの目が同じとき何もしない
        } else if (balance > 0 && answer == 'H') {
            hitCount++
            hitText.text = getString(R.string.hit_text) + hitCount
        } else if (balance < 0 && answer == 'L') {
            hitCount++
            hitText.text = getString(R.string.hit_text) + hitCount
        } else {
            loseCount++
            loseText.text = getString(R.string.lose_text) + loseCount
        }

        if (hitCount == 5) {
            resultText.text = "勝ち！おめでとうございます！"
            gameStarted = false
            continueOrNot()
        } else if (loseCount == 5) {
            resultText.text = "負け・・・"
            gameStarted = false
            continueOrNot()
        } else {
            //あたり・はずれの数が5未満のとき何もしない
        }
    }

    private fun showDroidCard(){
        //裏向きのままの相手のカードをめくる関数
        when (droidCard) {
            //droidCardに保存された数によって表示するカードが決まる
            1 -> droidCardImage.setImageResource(R.drawable.c01)
            2 -> droidCardImage.setImageResource(R.drawable.c02)
            3 -> droidCardImage.setImageResource(R.drawable.c03)
            4 -> droidCardImage.setImageResource(R.drawable.c04)
            5 -> droidCardImage.setImageResource(R.drawable.c05)
            6 -> droidCardImage.setImageResource(R.drawable.c06)
            7 -> droidCardImage.setImageResource(R.drawable.c07)
            8 -> droidCardImage.setImageResource(R.drawable.c08)
            9 -> droidCardImage.setImageResource(R.drawable.c09)
            10 -> droidCardImage.setImageResource(R.drawable.c10)
            11 -> droidCardImage.setImageResource(R.drawable.c11)
            12 -> droidCardImage.setImageResource(R.drawable.c12)
            13 -> droidCardImage.setImageResource(R.drawable.c13)
        }
    }

    private fun continueOrNot(){
        //まだゲームを続けるかやめるかを聞く関数
        AlertDialog.Builder(this) // FragmentではActivityを取得して生成
                .setMessage("もう一戦交えますか？")
                .setPositiveButton("はい") { dialog, which ->
                    //「はい」のとき勝ち負けの表示を消してonResume(ゲーム開始時の処理のところ)へ
                    resultText.text = ""
                    onResume()
                }
                .setNegativeButton("いいえ") { dialog, which ->
                    finish()
                }
                .show()
    }
}