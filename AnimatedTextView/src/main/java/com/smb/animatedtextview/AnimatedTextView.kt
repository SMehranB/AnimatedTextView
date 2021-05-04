package com.smb.animatedtextview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import kotlin.math.abs

class AnimatedTextView: androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context): super(context){
        initializeAttributes(context, null)
    }
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        initializeAttributes(context, attributeSet)
    }

    private var equalizerAnimDuration: Long = 0

    private var bareText = ""
    private var prefix: String = ""
    private var suffix: String = ""

    var animationDuration: Long = 1000

    private var onAnimationListener: AnimationListener? = null

    fun setPrefixSuffix(prefix: String, suffix: String){
        this.prefix = prefix
        this.suffix = suffix

        setText(bareText, prefix, suffix)
    }

    fun setText(value: String) {
        setText(value, prefix, suffix)
    }

    fun animateTo(targetValue: String){

        if(targetValue.isNotBlank()) {

            // IF THE NUMBER OF CHARS IS DIFFERENT BETWEEN THE INITIAL AND THE TARGET STRINGS, WE NEED THIS ANIMATION TO EQUALIZE THEM
            // IF THE VALUES ARE THE SAME IN SIZE, THIS ANIMATION WOULD BE MEANINGLESS SO IT IS NULL
            val equalizer: ValueAnimator? = getEqualizerAnimation(targetValue)
            //THIS IS THE MAIN ANIMATION THAT SETTLES EACH CHAR TO THE TARGET VALUE (CHAR BY CHAR FROM LEFT)
            val settlingAnimation = getSettlingAnimation(targetValue)

            onAnimationListener?.onAnimationStart(this@AnimatedTextView.text.toString(), bareText)

            val animatorSet = AnimatorSet()
            animatorSet.apply {
                addListener(object : MyAnimatorListener {
                    override fun onAnimationEnd(p0: Animator?) {
                        bareText = targetValue
                        text = "$prefix$targetValue$suffix"
                        onAnimationListener?.onAnimationEnd(text.toString(), bareText)
                    }
                })
                if(equalizer != null){
                    playSequentially(equalizer, settlingAnimation)
                }else{
                    play(settlingAnimation)
                }
                start()
            }
        }
    }

    fun addOnAnimationListener(animationListener: AnimationListener) {
        onAnimationListener = animationListener
    }

    fun getBareText(): String {
        return bareText
    }

    private fun initializeAttributes(context: Context, attributeSet: AttributeSet?) {

        val attr = context.theme.obtainStyledAttributes(attributeSet, R.styleable.AnimatedTextView, 0, 0)

        with(attr){
            prefix = getString(R.styleable.AnimatedTextView_atv_prefixToExclude) ?: ""
            suffix = getString(R.styleable.AnimatedTextView_atv_suffixToExclude) ?: ""

            animationDuration = getInt(R.styleable.AnimatedTextView_atv_animationDuration, animationDuration.toInt()).toLong()

            recycle()
        }

        bareText = text.toString()
        text = "$prefix$text$suffix"
    }

    private fun setText(textValue: String, prefix: String, suffix: String){

        var t = textValue

        if (!t.startsWith(prefix)) {
            t = prefix + t
        }

        if (!t.endsWith(suffix)) {
            t += suffix
        }

        bareText = textValue
        text = t
    }

    private fun getEqualizerAnimation(targetValue: String): ValueAnimator? {

        val charDiff = abs(targetValue.length - bareText.length)
        if(charDiff == 0) return null

        val stringBuilder = StringBuilder()

        // THE ANIMATION NEEDS TO PLAY EVENLY THROUGHOUT THE DURATION,
        // SO WE DIVIDE THE WHOLE DURATION AT HAND TO EQUAL RANGES,
        // AND CONCATENATE OR REMOVE ONE CHAR IN EACH RANGE AS THE ANIMATION PROGRESSES
        var rangeCounter = 1

        val range = animationDuration.div(2).div(charDiff).toInt()
        var rangeStart = 0
        var rangeEnd: Int = range

        // BECAUSE IF THE DIFFERENCE IN CHAR COUNTS IS 1 FOR EXAMPLE,
        // WE DON'T WANNA SPEND TOO MUCH TIME ON JUST ONE CHAR SO WE CALCULATE THE ANIMATION DURATION PROPORTIONALLY
        equalizerAnimDuration =  animationDuration.div( charDiff + targetValue.length).times(charDiff)

        val equalizer = ValueAnimator.ofInt(0, animationDuration.div(2).toInt())
        equalizer.apply {
            duration = equalizerAnimDuration
            addUpdateListener {

                stringBuilder.clear()

                val value = it.animatedValue as Int

                if (value in rangeStart..rangeEnd && rangeCounter <= abs(targetValue.length - bareText.length)) {

                    with(stringBuilder){
                        append(prefix)

                        val pattern = if(targetValue.length > bareText.length) {
                            targetValue.substring(0, bareText.length + rangeCounter)
                        }else{
                            bareText.substring(0, bareText.length - rangeCounter)
                        }

                        val randomString = generateRandomString(pattern)
                        append(randomString)

                        append(suffix)
                    }

                    text = stringBuilder.toString()

                } else {
                    rangeCounter++
                    rangeStart = rangeEnd
                    rangeEnd = range.times(rangeCounter)
                }
            }
        }

        return equalizer
    }

    private fun getSettlingAnimation(targetValue: String): ValueAnimator {

        val stringBuilder = StringBuilder()

        //AGAIN THE RANGE THING... REFER TO THE EQUALIZER ANIMATION
        val range = animationDuration.div(2).div(targetValue.length).toInt()
        var rangeStart = 0
        var rangeEnd = range

        var rangeCounter = 1

        val settlingAnimation = ValueAnimator.ofInt(0, animationDuration.div(2).toInt())
        settlingAnimation.apply {
            // THE REST OF THE DURATION IS DEDICATED TO THIS ANIMATION
            duration = animationDuration - equalizerAnimDuration
            addUpdateListener {

                stringBuilder.clear()
                val value = it.animatedValue as Int

                if (value in rangeStart..rangeEnd && rangeCounter <= targetValue.length) {

                    with(stringBuilder){
                        append(prefix)
                        append(targetValue.substring(0, rangeCounter))
                        val randomString = generateRandomString(targetValue.substring(rangeCounter, targetValue.length))
                        append(randomString)
                        append(suffix)
                    }

                    text = stringBuilder.toString()
                } else {
                    rangeCounter++
                    rangeStart = rangeEnd
                    rangeEnd = range.times(rangeCounter)
                }
            }
        }

        return settlingAnimation
    }

    private fun generateRandomString(pattern: String): String {

        val upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerChars = "abcdefghijklmnopqrstuvwxyz"

        val sb = StringBuffer()

        for(char in pattern){
            //EACH CHAR WILL BE REPLACED BY A CHAR OF ITS OWN KIND
            when {
                char.isLowerCase() -> {
                    val rnd = (lowerChars.indices).shuffled().last()
                    sb.append(lowerChars[rnd])
                }
                char.isUpperCase() -> {
                    val rnd = (upperChars.indices).shuffled().last()
                    sb.append(upperChars[rnd])
                }
                char.isDigit() -> {
                    val num = (0..9).shuffled().last()
                    sb.append(num)
                }
                else ->{
                    //DO NOT WANT TO ANIMATE NON-LETTER AND NON-DIGIT CHARS
                    sb.append(char)
                }
            }
        }

        return sb.toString()
    }

    interface AnimationListener {
        fun onAnimationStart(text: String, bareText: String)
        fun onAnimationEnd(text: String, bareText: String)
    }
}