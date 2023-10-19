package ru.nsu.fit.modao.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.ParticipantsAdapter
import ru.nsu.fit.modao.databinding.FragmentSelectSecondParticipantBinding
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.CreateExpenseViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SelectSecondParticipantFragment : BottomSheetDialogFragment(),
    ParticipantsAdapter.CustomListener,
    ParticipantsAdapter.InitListener {

    private var _binding: FragmentSelectSecondParticipantBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<SelectSecondParticipantFragmentArgs>()
    private val createExpenseViewModel: CreateExpenseViewModel by activityViewModels()
    @Inject
    lateinit var app: App
    private val adapter = ParticipantsAdapter()
    private var lastSelected: RadioButton? = null
    private var lastUser: ParticipantEvent? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSelectSecondParticipantBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        binding.buttonDone.setOnClickListener {
            //createExpenseViewModel.users.forEach { Log.d("MyTag", "${it.username!!} select ${it.selected} sponsor ${it.isSponsor}") }
            createExpenseViewModel.participants.value?.forEach { it.selected = it.id == app.userId ||
                    it.id == lastUser?.id }
            //createExpenseViewModel.participants.value?.forEach { Log.d("MyTag", "${it.username!!} select ${it.selected} sponsor ${it.isSponsor}") }
            findNavController().popBackStack(R.id.createAnExpenseFragment, inclusive = false)
        }
        binding.buttonGo.setOnClickListener {
            findNavController().navigate(SelectSecondParticipantFragmentDirections
                .actionSelectSecondParticipantFragmentToGroupMembersFragment(args.group))
        }

    }

    override fun onClickItem(button: RadioButton, user: ParticipantEvent) {
        if (user.isSponsor){
            //user.isSponsor = false
            lastSelected?.isChecked = false
            lastUser = null
        } else {
            lastSelected?.isChecked = false
            lastSelected = button
            //lastUser?.isSponsor = false
            button.isChecked = true
            //user.isSponsor = true
            lastUser = user
        }
    }

    override fun initItem(button: RadioButton, user: ParticipantEvent) {
        button.isChecked = false
        user.selected = false
    }
    private fun initRecycler(){
        val list = createExpenseViewModel.users.filter { it.id != app.userId }.toTypedArray().clone()
        if (list.isEmpty()){
            binding.buttonGo.visibility = View.VISIBLE
            binding.buttonDone.visibility = View.GONE
            binding.tipDialog.setText(R.string.addMoreMember)
        }
        adapter.setList(list)
        adapter.attachListener(this)
        adapter.attachInitListener(this)
        binding.newMemberRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.newMemberRecycler.adapter = adapter
    }
}