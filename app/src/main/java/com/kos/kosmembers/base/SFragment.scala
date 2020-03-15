package com.kos.kosmembers.base

import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.InputMethodManager
import android.widget.{CheckBox, EditText}
import androidx.annotation.{IdRes, NonNull, StringRes}
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.{BaseTransientBottomBar, Snackbar}
import com.kos.kosmembers.R


class SFragment extends Fragment with OnClickListener{

	def addClick(rootView:View, @IdRes btns:Int*): Unit ={
		btns.map(rootView.findViewById[View]).filter(_!=null).foreach(_.setOnClickListener(this))
	}

//	def navigate(direction: NavDirections): Unit ={
//		val nav = NavHostFragment.findNavController(this)
//		val action = nav.getCurrentDestination.getAction(direction.getActionId)
//		if (action != null ) {
//			nav.navigate(direction)
//		}
//	}

	def viewModels = new ViewModelProvider(this)

	def viewModelsOfActivity = new ViewModelProvider(getActivity)

	def viewModelsOfActivity(@NonNull factory: ViewModelProvider.Factory)= new ViewModelProvider(getActivity,factory)

	override def onClick(v: View): Unit = {

	}

	//----------------------------------------------------------


	def checkValue(@IdRes viewId:Int, index: Int):Int = if (getView.findViewById[CheckBox](viewId).isChecked) 1 << index else 0

	def findEdit(@IdRes viewId:Int):EditText = {
		getView.findViewById[EditText](viewId)
	}

	def editText(@IdRes viewId:Int):String = findEdit(viewId).getText.toString

	def hideKeyboard(): Unit = {
		val view = getActivity.getCurrentFocus
		if (view != null) {
			val imm = getActivity.getSystemService(Context.INPUT_METHOD_SERVICE).asInstanceOf[InputMethodManager]
			imm.hideSoftInputFromWindow(view.getWindowToken, 0)
		}
	}


	def snack(text:String): Unit ={
		val snack = Snackbar.make(getView,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.show()
	}


	def snack(@StringRes text:Int): Unit ={
		val snack = Snackbar.make(getView,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.show()
	}

	def snackError(@StringRes text:Int): Unit ={
		val snack = Snackbar.make(getView,text,BaseTransientBottomBar.LENGTH_SHORT)
		snack.setBackgroundTint(ContextCompat.getColor(getActivity,R.color.errorSnackColor))
		snack.show()
	}

}
