package example.page;

import org.apache.click.Page;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.TextField;
import org.apache.click.util.Bindable;

public class LoginPage extends Page {
    @Bindable protected Form form = new Form("loginForm");

    public LoginPage() {

    	//form = new Form("loginForm"); // Assign a name to the Form
    	
        TextField usernameField = new TextField("username", "Username", true);
        TextField passwordField = new TextField("password", "Password", true);
        form.add(usernameField);
        form.add(passwordField);

        form.add(new Submit("login", "Login", this, "onLoginClick"));
        addControl(form); // Bind the Form to the template manually
    }

    public boolean onLoginClick() {
        String username = form.getFieldValue("username");
        String password = form.getFieldValue("password");

        // Here, you would typically validate the credentials against a database or other authentication mechanism.
        // This example assumes any non-empty username and password is valid.
        if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            getContext().setSessionAttribute("isLoggedIn", true);
            setRedirect(UserPage.class);
            return false;
        } else {
            form.setError("Invalid credentials");
            return true;
        }
    }
}
