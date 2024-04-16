package com.SerenityBDDForGemini.navigation;

import com.SerenityBDDForGemini.pages.clovek.ClovekLogin;
import com.SerenityBDDForGemini.pages.retina.RetinaLogin;

public class NavigateTo {
    final ClovekLogin clovekLogin = new ClovekLogin();
    final RetinaLogin retinaLogin = new RetinaLogin();

    public void theClovekLoginPage() {
        clovekLogin.open();
    }

    public void theRetinaLoginPage() {
        retinaLogin.open();
    }
}
