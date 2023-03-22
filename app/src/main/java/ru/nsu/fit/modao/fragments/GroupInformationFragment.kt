package ru.nsu.fit.modao.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.nsu.fit.modao.databinding.FragmentGroupInformationBinding
import ru.nsu.fit.modao.repository.Repository
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import ru.nsu.fit.modao.viewmodels.RepositoryViewModelFactory


class GroupInformationFragment : Fragment() {
    private var _binding: FragmentGroupInformationBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var app: App
    private val args by navArgs<GroupInformationFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGroupInformationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app = requireActivity().application as App
        val repository = Repository(app)
        mainViewModel = ViewModelProvider(
            requireActivity(),
            RepositoryViewModelFactory(repository)
        )[MainViewModel::class.java]

        mainViewModel.getGroupInfo(args.group.id!!)
        mainViewModel.groupInfo.observe(viewLifecycleOwner) {
            binding.groupName.text = it.groupName
            binding.groupUuid.text = it.uuid
        }
    }
}