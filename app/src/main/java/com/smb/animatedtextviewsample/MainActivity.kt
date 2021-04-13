package com.smb.animatedtextviewsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAnimate1.setOnClickListener {
            txtPriceOne.animateTo("731,984,625")
            txtPriceTwo.animateTo("937.75")
            txtDate.animateTo("09/28/1998")
            txtName.animateTo("Conan Doyle")
            txtEmail.animateTo("jimmie_the_clown")
            txtPhoneNumber.animateTo("(987) 65 43")
        }

        btnAnimate2.setOnClickListener {
            txtPriceOne.animateTo("123,456")
            txtPriceTwo.animateTo("123,456.00")
            txtDate.animateTo("04/14/2021")
            txtName.animateTo("Anthony Hopkins")
            txtEmail.animateTo("some_username")
            txtPhoneNumber.animateTo("(123) 45 67")
        }
    }
}