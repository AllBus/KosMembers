package com.kos.kosmembers

import androidx.lifecycle.{Observer, ViewModelProviders}
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.kos.kosmembers.base.SFragment
import com.kos.kosmembers.viewmodels.MembersViewModel
import members.proto.members.SuggestBook

object MembersFragment {
	def newInstance = new MembersFragment
}

class MembersFragment extends SFragment {
	private var viewModel : MembersViewModel = _

	override def onCreateView(@NonNull inflater: LayoutInflater, @Nullable container: ViewGroup, @Nullable savedInstanceState: Bundle): View = inflater.inflate(R.layout.members_fragment, container, false)

	override def onActivityCreated(@Nullable savedInstanceState: Bundle): Unit = {
		super.onActivityCreated(savedInstanceState)
		viewModel = viewModels.get(classOf[MembersViewModel])

		viewModel.checkServer.observe(this, new Observer[Boolean] {
			override def onChanged(t: Boolean): Unit = {
				Option(getView.findViewById[View](R.id.btnDone)).foreach(_.setEnabled(t))
			}
		})

		viewModel.sendServer.observe(this, new Observer[Int] {
			override def onChanged(text: Int): Unit = {
				if (text!=0) {
					snack(text)
					viewModel.resetAnswer()
				}
			}
		})
		viewModel.test()

	}

	override def onViewCreated(view: View, savedInstanceState: Bundle): Unit = {
		super.onViewCreated(view, savedInstanceState)
		addClick(view, R.id.btnDone)
	}

	override def onClick(v: View): Unit = {
		v.getId match {
			case R.id.btnDone ⇒
				val nameEdit = getView.findViewById[EditText](R.id.nameEdit)
				val bookEdit = getView.findViewById[EditText](R.id.bookEdit)
				val commentEdit = getView.findViewById[EditText](R.id.commentEdit)

				viewModel.suggestBook(SuggestBook(nameEdit.getText.toString,bookEdit.getText.toString,commentEdit.getText.toString, viewModel.deviceInfo))
			case _ ⇒
		}
	}
}