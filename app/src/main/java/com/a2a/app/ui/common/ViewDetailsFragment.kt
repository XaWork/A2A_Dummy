package com.a2a.app.ui.common

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.a2a.app.R
import com.a2a.app.data.model.CommonModel
import com.a2a.app.databinding.FragmentViewDetailsBinding
import com.a2a.app.ui.components.A2AButton
import com.a2a.app.ui.components.A2ATopAppBar
import com.a2a.app.ui.theme.ScreenPadding
import com.a2a.app.ui.theme.SpaceBetweenViewsAndSubViews
import com.google.gson.Gson


class ViewDetailsFragment : Fragment(R.layout.fragment_view_details) {

    private lateinit var viewBinding: FragmentViewDetailsBinding
    private lateinit var details: CommonModel
    private lateinit var toolbarTitle: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentViewDetailsBinding.bind(view)
        getDetails()
        //setToolbar()
        setDetails()
    }

    private fun getDetails() {
        val args: ViewDetailsFragmentArgs by navArgs()
        details = Gson().fromJson(args.details, CommonModel::class.java)
        toolbarTitle = args.name.toString()
    }

    private fun setDetails() {
        viewBinding.viewDetailsComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ViewDetailsScreen()
            }
        }
    }

    private fun moveToBookScreen() {
        findNavController().navigate(R.id.action_global_bookFragment)
    }

    @Composable
    fun ViewDetailsScreen() {
        Scaffold(topBar = {
            A2ATopAppBar(toolbarTitle) {
                findNavController().popBackStack()
            }
        },
            content = {
                ContentViewDetails()
            })
    }

    @Composable
    fun ContentViewDetails() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .scrollable(orientation = Orientation.Vertical,
                        state = rememberScrollState())
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(details.file).build(),
                    contentDescription = "",
                    placeholder = painterResource(id = R.drawable.image_placeholder),
                    error = painterResource(id = R.drawable.image_placeholder),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(SpaceBetweenViewsAndSubViews))

                Text(
                    text = details.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = Html.fromHtml(details.description, 0).toString(),
                    modifier = Modifier
                        .padding(ScreenPadding)
                        .fillMaxWidth(),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            A2AButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                title = "Book Now"
            ) {
                moveToBookScreen()
            }
        }
    }

    @Preview
    @Composable
    fun ViewDetailsScreenPreview() {
        ViewDetailsScreen()
    }
}