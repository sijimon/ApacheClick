package example.page;

import org.apache.click.Page;
import org.apache.click.ActionListener;
import org.apache.click.Control;
import org.apache.click.control.ActionLink;
import org.apache.click.control.Column;
import org.apache.click.control.Form;
import org.apache.click.control.Submit;
import org.apache.click.control.Table;
import org.apache.click.control.TextField;
import org.apache.click.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class UserPage extends Page {

    private Form form = new Form("form");
    private Table table = new Table("table");

    public UserPage() {
        TextField nameField = new TextField("name", true);
        TextField emailField = new TextField("email", true);

        form.add(nameField);
        form.add(emailField);
        form.add(new Submit("ok", "  OK  ", this, "onOkClicked"));

        addControl(form);

        Column nameColumn = new Column("name");
        Column emailColumn = new Column("email");

        table.addColumn(nameColumn);
        table.addColumn(emailColumn);

        Column removeColumn = new Column("removeLink", "Remove");
        removeColumn.setEscapeHtml(false);
        table.addColumn(removeColumn);

        table.setDataProvider(new DataProvider() {
            @SuppressWarnings("unchecked")
            public List<User> getData() {
                return (List<User>) getContext().getSessionAttribute("users");
            }
        });

        addControl(table);
    }

    @SuppressWarnings("unchecked")
    public boolean onOkClicked() {
        if (form.isValid()) {
            String name = form.getFieldValue("name");
            String email = form.getFieldValue("email");

            List<User> users = (List<User>) getContext().getSessionAttribute("users");
            if (users == null) {
                users = new ArrayList<>();
                getContext().setSessionAttribute("users", users);
            }

            ActionLink removeLink = new ActionLink("remove_" + users.size(), this, "onRemoveClicked");
            removeLink.setLabel("Remove");
            addControl(removeLink);
            users.add(new User(name, email, removeLink.toString()));
            return true;
        } else {
            return false;
        }
    }


    @SuppressWarnings("unchecked")
    private void onRemoveClicked(String userId) {
        List<User> users = (List<User>) getContext().getSessionAttribute("users");
        if (users != null) {
            users.removeIf(user -> String.valueOf(user.getId()).equals(userId));
        }
    }
    
    public void onGet() {
        String parameter = getContext().getRequestParameter("actionLink");
        if (parameter != null && parameter.startsWith("remove_")) {
            onRemoveClicked(parameter.substring(7));
        }
    	System.out.println("Get is called para value :"+parameter);
    }
    
    @Override
    public void onInit() {
        super.onInit();

        Boolean isLoggedIn = (Boolean) getContext().getSessionAttribute("isLoggedIn");
        if (isLoggedIn == null || !isLoggedIn) {
            setRedirect(LoginPage.class);
        }
    }
}
