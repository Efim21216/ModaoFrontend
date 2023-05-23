package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.R
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.GroupAdapter
import ru.nsu.fit.modao.databinding.FilterGroupsBinding
import ru.nsu.fit.modao.databinding.FragmentGroupsBinding
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.viewmodels.MainViewModel

@AndroidEntryPoint
class GroupsFragment : Fragment(), AdapterListener<Group> {
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!
    private val adapter: GroupAdapter = GroupAdapter()
    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<GroupsFragmentArgs>()
    private lateinit var window: PopupWindow
    private lateinit var bindingPopupWindow: FilterGroupsBinding
    private var lastSelect: RadioButton? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGroupsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopupWindow()
        if (args.notification){
            mainViewModel.getGroupInfo(args.groupId)
            mainViewModel.groupInfo.observe(viewLifecycleOwner) {
                val action = GroupsFragmentDirections.actionGroupsFragmentToGroupInfoFragment(it)
                action.notification = true
                findNavController().navigate(action)
            }
        }

        mainViewModel.getUserGroups(0)
        adapter.attachListener(this)
        mainViewModel.userGroups.observe(viewLifecycleOwner){
            adapter.setGroups(it)
        }
        binding.groupsRecycler.layoutManager = LinearLayoutManager(this.context,
            RecyclerView.VERTICAL, false)
        binding.groupsRecycler.adapter = adapter
        binding.buttonAddGroup.setOnClickListener {
            findNavController().navigate(GroupsFragmentDirections.actionGroupsFragmentToCreateGroupFragment())
        }
        binding.buttonAddByUUID.setOnClickListener {
            val uuid = binding.editGroupUUID.text.toString()
            mainViewModel.addToGroup(uuid)
        }
        mainViewModel.tipMessage.observe(viewLifecycleOwner) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(it)
            builder.setPositiveButton("OK") { _, _ -> }
            builder.create().show()
        }
        binding.filterIcon.setOnClickListener {
            window.showAsDropDown(binding.filterIcon, -250, 0)
        }
    }

    override fun onClickItem(item: Group) {
        findNavController().navigate(GroupsFragmentDirections.actionGroupsFragmentToGroupInfoFragment(item))
    }
    private fun setPopupWindow(){
        window = PopupWindow(context)
        val view = LayoutInflater.from(context).inflate(R.layout.filter_groups, binding.root, false)
        bindingPopupWindow = FilterGroupsBinding.bind(view)
        window.contentView = bindingPopupWindow.root
        window.isFocusable = true
        bindingPopupWindow.active.isChecked = true
        lastSelect = bindingPopupWindow.active
        setButton()
    }
    private fun setButton() {
        bindingPopupWindow.active.setOnClickListener {
            if (switch(bindingPopupWindow.active)) {
                mainViewModel.getUserGroups(0)
            }
        }
        bindingPopupWindow.archive.setOnClickListener {
            if (switch(bindingPopupWindow.archive)) {
                mainViewModel.getUserGroups(1)
            }
        }
        bindingPopupWindow.allGroups.setOnClickListener {
            if (switch(bindingPopupWindow.allGroups)) {
                mainViewModel.getUserGroups(2)
            }
        }
    }
    private fun switch(radioButton: RadioButton): Boolean {
        if (lastSelect !== radioButton) {
            lastSelect?.isChecked = false
            lastSelect = radioButton
            return true
        }
        return false
    }
}