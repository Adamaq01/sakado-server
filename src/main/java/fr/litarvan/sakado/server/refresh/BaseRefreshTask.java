package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.List;

import fr.litarvan.sakado.server.data.Identifiable;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.UserManager;

public abstract class BaseRefreshTask extends RefreshTask
{
    private List<String> seen;

    public BaseRefreshTask()
    {
        this.seen = new ArrayList<>();
    }

    @Override
    public void refresh(UserManager userManager)
    {
        for (User user : userManager.getLoggedUsers())
        {
            if (user.getName() == null) // Logged but not fetched
            {
                continue;
            }

            refresh(user);
        }
    }

    public abstract void refresh(User user);

    protected void removeSeen(User user, List<? extends Identifiable> list)
    {
        list.removeIf(e -> {
            String id = user.getUsername() + "-" + e.getId();

            if (!seen.contains(id))
            {
                seen.add(id);
                return false;
            }

            return true;
        });
    }
}
