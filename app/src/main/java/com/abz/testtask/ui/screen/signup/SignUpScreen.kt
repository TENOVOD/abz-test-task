package com.abz.testtask.ui.screen.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abz.testtask.R
import com.abz.testtask.internet.ConnectivityObserver
import com.abz.testtask.navigation.LocalNavController
import com.abz.testtask.navigation.NavigationScreen
import com.abz.testtask.ui.bottombar.BottomBarScreen
import com.abz.testtask.ui.bottombar.MainButtonBar
import com.abz.testtask.ui.bottomsheets.AddPhotoBottomSheet
import com.abz.testtask.ui.button.PrimaryButton
import com.abz.testtask.ui.situational.NoInternetConnectionScreen
import com.abz.testtask.ui.situational.signupresult.SignUpResultScreen
import com.abz.testtask.ui.topbars.RequestTopBar
import com.abz.testtask.viewmodel.SignUpViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val IOScope = CoroutineScope(Dispatchers.IO)
    val scrollState = rememberScrollState()

    val navController = LocalNavController.current

    // For listening network and position
    val networkStatus by viewModel.networkStatus.collectAsState()
    val positions by viewModel.positions.collectAsState()


    //For work with keyboard
    val isImeVisible = WindowInsets.isImeVisible
    val keyboardController = LocalSoftwareKeyboardController.current


    // Values from viewModel for textfields, radiobutton and photo
    val name by viewModel.name
    val email by viewModel.email
    val phone by viewModel.phone
    val positionId by viewModel.positionId
    val photoName by viewModel.photoName

    val nameError by viewModel.nameError
    val emailError by viewModel.emailError
    val phoneError by viewModel.phoneError
    val photoError by viewModel.photoError

    val isLoading by viewModel.isLoading
    val isRegistrationSuccess by viewModel.registrationSuccess
    val hasShowedRegistrationResult by viewModel.hasShowedRegistrationResult


    //For bottom dialog
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var isClickedOnGallery by remember { mutableStateOf(false) }
    var isClickedOnCamera by remember { mutableStateOf(false) }

    //For work with uploading image/photo
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val readStoragePermissionState =
        rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setPhotoUri(context, uri)
    }

    var cameraImageUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            viewModel.setPhotoUri(context, cameraImageUri)
        }
    }


    //Triggers for showing NoInternetConnectionScreen, when the Internet is unavailable
    var hasInternetConnection by remember {
        mutableStateOf(false)
    }
    var hasShowNoInternetConnectionScreen by remember {
        mutableStateOf(false)
    }

    val hideMainContentWithAnim by animateFloatAsState(
        if (hasShowNoInternetConnectionScreen) 0f else 1f,
        animationSpec = tween(200)
    )
    val showNoInternetConnectionWithAnim by animateFloatAsState(
        if (hasShowNoInternetConnectionScreen) 1f else 0f,
        animationSpec = tween(200)
    )
    val showRegistrationResultScreenWithAnimation by animateFloatAsState(
        if (hasShowedRegistrationResult) 1f else 0f,
        animationSpec = tween(200)
    )


    /*
        Listening cameraPermissionState
        When user provide permission -> open camera
     */
    LaunchedEffect(cameraPermissionState.status) {
        if (cameraPermissionState.status.isGranted&&isClickedOnCamera) {
            IOScope.launch {
                cameraImageUri = viewModel.createImageUri(context)
                cameraLauncher.launch(cameraImageUri!!)
            }
            coroutineScope.launch {
                sheetState.hide()
                showBottomSheet = false
            }
            isClickedOnCamera=false
        }
    }


    /*
       Listening readStoragePermissionState
       When user provide permission -> open gallery in bottom dialog
    */
    LaunchedEffect(readStoragePermissionState.status) {
        if (readStoragePermissionState.status.isGranted&&isClickedOnGallery) {
            IOScope.launch {
                galleryLauncher.launch("image/*")
            }
            coroutineScope.launch {

                sheetState.hide()
                showBottomSheet = false
            }
            isClickedOnGallery=false
        }
    }


    /*
       Listening networkStatus
       When network unavailable -> show NoInternetScreen
       When network available -> hide NoInternetScreen
    */
    LaunchedEffect(networkStatus) {
        if (networkStatus == ConnectivityObserver.Status.Unavailable) {
            hasInternetConnection = true
            hasShowNoInternetConnectionScreen = true
        } else {
            hasInternetConnection = false
        }
    }

    if (!hasShowedRegistrationResult) {
        //Main Screen with registration form
        Scaffold(
            modifier = Modifier
                .alpha(hideMainContentWithAnim)
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding(),
            topBar = {
                RequestTopBar(textSrc = R.string.Working_with_POST_request)
            },
            bottomBar = {
                AnimatedVisibility(!isImeVisible) {
                    MainButtonBar(screenName = BottomBarScreen.SIGN_UP_SCREEN)
                }
            }

        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(vertical = 32.dp)
                            .padding(bottom = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        //Block with TextFields
                        BlockOfCredits(
                            nameValue = name,
                            emailValue = email,
                            phoneValue = phone,
                            nameError = nameError,
                            phoneError = phoneError,
                            emailError = emailError,
                            onChangeName = viewModel::onNameChanged,
                            onChangeEmail = viewModel::onEmailChanged,
                            onChangePhone = viewModel::onPhoneChanged
                        )
                        //Block with Radio Button (Position Selector)
                        if (positions.isNotEmpty()) {
                            if (positionId == null) viewModel.onPositionSelected(1)
                            BlockOfPositionSelector(
                                positions = positions,
                                selectedPositionId = positionId!!,
                                onSelectedPositionId = viewModel::onPositionSelected
                            )
                        }

                        //Upload Image TextField
                        UploadImageTextField(
                            value = photoName,
                            isError = photoError,
                            supportingErrorTextSrc = R.string.Photo_is_required,
                            labelText = R.string.Upload_your_photo,
                            buttonTextSrc = R.string.Upload
                        ) {
                            showBottomSheet = true
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    //Button Sign Up
                    PrimaryButton(textSrc = R.string.Sign_up, isDisabled = isLoading) {
                        if (!isLoading) {
                            viewModel.registerUser()
                            if (isImeVisible) {
                                keyboardController?.hide()
                            }
                        }
                    }
                }
            }


            /*
                Upload Image Bottom Dialog
             */
            AddPhotoBottomSheet(
                isActive = showBottomSheet,
                sheetState = sheetState,
                openCamera = {
                    isClickedOnCamera=true
                    if (cameraPermissionState.status.isGranted) {
                        IOScope.launch {
                            cameraImageUri = viewModel.createImageUri(context)
                            cameraLauncher.launch(cameraImageUri!!)
                        }
                        coroutineScope.launch {
                            sheetState.hide()
                            showBottomSheet = false
                        }
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                openGallery = {
                    isClickedOnGallery=true
                    if (readStoragePermissionState.status.isGranted) {
                        IOScope.launch {
                            galleryLauncher.launch("image/*")
                        }
                        coroutineScope.launch {

                            sheetState.hide()
                            showBottomSheet = false
                        }
                    } else {
                        readStoragePermissionState.launchPermissionRequest()
                    }
                },
                onDismiss = { showBottomSheet = false })

        }
    } else {

        /*
            If we have registration result -> show this screen
         */
        SignUpResultScreen(
            modifier = Modifier.alpha(showRegistrationResultScreenWithAnimation),
            isSuccess = isRegistrationSuccess,
            onClickSuccess = {
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate(NavigationScreen.USERS_SCREEN.name)
                }
                viewModel.closeRegistrationResult()
            },
            onClickFailed = {
                CoroutineScope(Dispatchers.Main).launch {
                    navController.navigate(NavigationScreen.SIGN_UP_SCREEN.name)
                }
                viewModel.closeRegistrationResult()
            },
            onClose = {
                viewModel.closeRegistrationResult()
            }
        )
    }

    /*
      When internet is unavailable -> show this screen
    */
    NoInternetConnectionScreen(
        modifier = Modifier.alpha(showNoInternetConnectionWithAnim),
        isStarted = !hasShowNoInternetConnectionScreen
    ) {

        if (!hasInternetConnection) {
            if (positions.isEmpty()) viewModel.loadPositions()
            hasShowNoInternetConnectionScreen = false
        }
    }


}
