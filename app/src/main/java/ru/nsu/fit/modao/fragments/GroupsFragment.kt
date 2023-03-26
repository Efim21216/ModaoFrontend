package ru.nsu.fit.modao.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.modao.adapter.AdapterListener
import ru.nsu.fit.modao.adapter.GroupAdapter
import ru.nsu.fit.modao.databinding.FragmentGroupsBinding
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory
import ru.nsu.fit.modao.viewmodels.MainViewModel

class GroupsFragment : Fragment(), AdapterListener<Group> {
    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!
    private var app: App? = null
    private val adapter: GroupAdapter = GroupAdapter()
    private lateinit var mainViewModel: MainViewModel
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

        app = activity?.application as App
        val repository = Repository(app!!)
        val viewModelFactory = RepositoryViewModelFactory(repository)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        mainViewModel.getUserGroups()
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
    }

    override fun onClickItem(item: Group) {
        findNavController().navigate(GroupsFragmentDirections.actionGroupsFragmentToGroupInfoFragment(item))
    }
}