package pe.indigital.tunki.core.example.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private HomeController homeController;

    @Mock
    private Model model;

    @Before
    public void setUp() {
        homeController = new HomeController();
    }

    @Test
    public void homePage_ShouldReturnIndex_WhenStart() {
        when(model.addAttribute(any(), any())).thenReturn(model);
        String result = homeController.homePage(model);
        assertEquals("home/index", result);
    }

}
