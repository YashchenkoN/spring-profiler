package tech.yashchenkon.utils.profiling;

/**
 * @author Nikolay Yashchenko
 */
public class ProfilingSwitcherImpl implements ProfilingSwitcherMXBean {

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
