package ru.nsu.fit.modao.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.adapter.DebtAdapter
import ru.nsu.fit.modao.databinding.FragmentUserExpensesInGroupBinding
import ru.nsu.fit.modao.models.UserDebt
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject
@AndroidEntryPoint
class UserExpensesInGroupFragment : Fragment() {
    private var _binding: FragmentUserExpensesInGroupBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
    private val adapter = DebtAdapter()
    private val args by navArgs<UserExpensesInGroupFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserExpensesInGroupBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getUserDebtInGroup(userId = app.userId, groupId = args.group.id!!)
        val list: Array<UserDebt> = mainViewModel.userExpenses.value ?: arrayOf()
        adapter.setList(list.toCollection(ArrayList()))
        binding.expensesRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.expensesRecycler.adapter = adapter
        mainViewModel.userExpenses.observe(viewLifecycleOwner) {
            adapter.setList(it.toCollection(ArrayList()))
        }
    }
}