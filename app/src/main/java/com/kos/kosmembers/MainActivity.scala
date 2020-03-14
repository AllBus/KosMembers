package com.kos.kosmembers

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import com.kos.kosmembers.base.SActivity
import com.kos.kosmembers.viewmodels.MembersViewModel
import members.proto.members.SuggestBook

class MainActivity extends SActivity {

	private var viewModel : MembersViewModel = _

	override protected def onCreate(savedInstanceState: Bundle): Unit = {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		viewModel = viewModels.get(classOf[MembersViewModel])

		addClick(R.id.btnDone)

		viewModel.checkServer.observe(this, new Observer[Boolean] {
			override def onChanged(t: Boolean): Unit = {
				Option(findViewById[View](R.id.btnDone)).foreach(_.setEnabled(t))
			}
		})

		viewModel.sendServer.observe(this, new Observer[String] {
			override def onChanged(text: String): Unit = {
				if (text!=null && text!="") {
					snack(findViewById[View](R.id.layout), text)
					viewModel.resetAnswer()
				}
			}
		})

		viewModel.test()
	}

	override def onClick(v: View): Unit = {
		v.getId match {
			case R.id.btnDone ⇒
				val nameEdit = findViewById[EditText](R.id.nameEdit)
				val bookEdit = findViewById[EditText](R.id.bookEdit)
				val commentEdit = findViewById[EditText](R.id.commentEdit)

				viewModel.suggestBook(SuggestBook(nameEdit.getText.toString,bookEdit.getText.toString,commentEdit.getText.toString))
			case _ ⇒
		}
	}
}