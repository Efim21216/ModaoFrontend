package ru.nsu.fit.modao.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.fit.modao.databinding.FragmentGroupInformationBinding
import ru.nsu.fit.modao.utils.App
import ru.nsu.fit.modao.viewmodels.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class GroupInformationFragment : Fragment() {
    private var _binding: FragmentGroupInformationBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var app: App
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

        mainViewModel.getGroupInfo(args.group.id!!)
        mainViewModel.groupInfo.observe(viewLifecycleOwner) {
            binding.groupName.text = it.groupName
            binding.groupUuid.text = it.uuid
            binding.groupDescription.text = it.description
        }
        mainViewModel.getListOrganizers(args.group.id!!)
        mainViewModel.organizers.observe(viewLifecycleOwner) {
            val isOrganizer = it.any { org -> org.id == app.userId }
            if (isOrganizer) {
                binding.titleGroupUuid.visibility = View.VISIBLE
                binding.groupUuid.visibility = View.VISIBLE
            }
        }
    }
}