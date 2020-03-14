package com.kos.kosmembers.base

import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import androidx.annotation.{IdRes, StringRes}
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.{BaseTransientBottomBar, Snackbar}
import com.kos.kosmembers.R

class SActivity extends AppCompatActivity with OnClickListener{

	def addClick(@IdRes btns:Int*): Unit ={
		btns.map(findViewById[View]).foreach(_.setOnClickListener(this))
	}

	def viewModels() = new ViewModelProvider(this)

	override def onClick(v: View): Unit = {}

	def snack(parent:View, text:String): Unit ={
		val snack = Snackbar.make(parent,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.show()
	}

	def snack(parent:View, @StringRes text:Int): Unit ={
		val snack = Snackbar.make(parent,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.show()
	}

	def snackError(parent:View, text:String): Unit ={
		val snack = Snackbar.make(parent,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.setBackgroundTint(ContextCompat.getColor(this,R.color.errorSnackColor))
		snack.show()
	}

	def snackError(parent:View, @StringRes text:Int): Unit ={
		val snack = Snackbar.make(parent,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.setBackgroundTint(ContextCompat.getColor(this,R.color.errorSnackColor))
		snack.show()
	}

	def hideKeyboard(): Unit = {
		val view = this.getCurrentFocus
		if (view != null) {
			val imm = getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
			imm.hideSoftInputFromWindow(view.getWindowToken, 0)
		}
	}
}
