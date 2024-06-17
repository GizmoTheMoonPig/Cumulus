package com.aetherteam.cumulus.api;

import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.ScreenAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.TabButtonAccessor;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;

import java.util.function.BooleanSupplier;

public class Menu {
    private final ResourceLocation icon;
    private final Component name;
    private final TitleScreen screen;
    private final BooleanSupplier condition;
    private final Runnable apply;
    private final Music music;
    private final Background background;

    public Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition) {
        this(icon, name, screen, condition, new Properties());
    }

    public Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition, Properties properties) {
        this(icon, name, screen, condition, properties.apply, properties.music, properties.background);
    }

    public Menu(ResourceLocation icon, Component name, TitleScreen screen, BooleanSupplier condition, Runnable apply, Music music, Background background) {
        this.icon = icon;
        this.name = name;
        this.screen = screen;
        this.condition = condition;
        this.apply = apply;
        this.music = music;
        this.background = background;
    }

    /**
     * @return The {@link ResourceLocation} for the icon that this menu has in the {@link com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen selection screen}.
     */
    public ResourceLocation getIcon() {
        return this.icon;
    }

    /**
     * @return The {@link Component} for the name that this menu has in the {@link com.aetherteam.cumulus.client.gui.screen.MenuSelectionScreen selection screen}.
     */
    public Component getName() {
        return this.name;
    }

    /**
     * @return The {@link TitleScreen} to display for this menu.
     */
    public TitleScreen getScreen() {
        return this.screen;
    }

    /**
     * @return The {@link BooleanSupplier} condition for when this menu should be able to display.
     */
    public BooleanSupplier getCondition() {
        return this.condition;
    }

    /**
     * @return {@link Runnable} for a function to run when this menu is applied.
     */
    public Runnable getApply() {
        return this.apply;
    }

    /**
     * @return {@link Music} to run in the menu.
     */
    public Music getMusic() {
        return this.music;
    }

    /**
     * @return A {@link Background} tied to this menu; this is used to replace dirt backgrounds.
     */
    public Background getBackground() {
        return this.background;
    }

    /**
     * @return The {@link ResourceLocation} of the {@link Menu}'s full registry ID.
     */
    public ResourceLocation getId() {
        return Cumulus.MENU_REGISTRY.getKey(this);
    }

    /**
     * @return The {@link String} of the {@link Menu}'s full registry ID, converted from a {@link ResourceLocation} from {@link Menu#getId()}.
     */
    public String toString() {
        return this.getId().toString();
    }

    public static class Properties {
        private Runnable apply = () -> {};
        private Music music = Musics.MENU;
        private Background background = Background.MINECRAFT;

        /**
         * @see Menu#getApply()
         */
        public Properties apply(Runnable apply) {
            this.apply = apply;
            return this;
        }

        /**
         * @see Menu#getMusic()
         */
        public Properties music(Music music) {
            this.music = music;
            return this;
        }

        /**
         * @see Menu#getBackground()
         */
        public Properties background(Background background) {
            this.background = background;
            return this;
        }

        public static Properties propertiesFromType(Menu menu) {
            Properties props = new Properties();
            props.apply = menu.apply;
            props.music = menu.music;
            props.background = menu.background;
            return props;
        }
    }

    public static class Background {
        private static final ResourceLocation DEFAULT_REGULAR_BACKGROUND = Screen.MENU_BACKGROUND;
        private static final ResourceLocation DEFAULT_HEADER_SEPARATOR = Screen.HEADER_SEPARATOR;
        private static final ResourceLocation DEFAULT_FOOTER_SEPARATOR = Screen.FOOTER_SEPARATOR;
        private static final WidgetSprites DEFAULT_TAB_BUTTON = TabButtonAccessor.cumulus$getSprites();

        private ResourceLocation regularBackground = DEFAULT_REGULAR_BACKGROUND;
        private ResourceLocation headerSeparator = DEFAULT_HEADER_SEPARATOR;
        private ResourceLocation footerSeparator = DEFAULT_FOOTER_SEPARATOR;
        private WidgetSprites tabButton = DEFAULT_TAB_BUTTON;

        public static final Background MINECRAFT = new Background()
                .regularBackground(DEFAULT_REGULAR_BACKGROUND)
                .headerSeparator(DEFAULT_HEADER_SEPARATOR)
                .footerSeparator(DEFAULT_FOOTER_SEPARATOR)
                .tabButton(DEFAULT_TAB_BUTTON);

        /**
         * Applies a background through mixin accessors.
         * @param background The {@link Background} to apply.
         */
        public static void apply(Background background) {
            ScreenAccessor.cumulus$setMenuBackground(background.getRegularBackground());
            ScreenAccessor.cumulus$setHeaderSeparator(background.getHeaderSeparator());
            ScreenAccessor.cumulus$setFooterSeparator(background.getFooterSeparator());
            TabButtonAccessor.cumulus$setSprites(background.getTabButton());
        }

        /**
         * Resets the background to the default {@link Background#MINECRAFT} one.
         */
        public static void reset() {
            apply(MINECRAFT);
        }

        /**
         * @see Background#getRegularBackground()
         */
        public Background regularBackground(ResourceLocation regularBackground) {
            this.regularBackground = regularBackground;
            return this;
        }

        /**
         * @see Background#getHeaderSeparator()
         */
        public Background headerSeparator(ResourceLocation headerSeparator) {
            this.headerSeparator = headerSeparator;
            return this;
        }

        /**
         * @see Background#getFooterSeparator()
         */
        public Background footerSeparator(ResourceLocation footerSeparator) {
            this.footerSeparator = footerSeparator;
            return this;
        }

        /**
         * @see Background#getTabButton()
         */
        public Background tabButton(WidgetSprites tabButton) {
            this.tabButton = tabButton;
            return this;
        }

        /**
         * @return The {@link ResourceLocation} for the regular dirt background texture to replace.
         */
        public ResourceLocation getRegularBackground() {
            return this.regularBackground;
        }

        /**
         * @return The {@link ResourceLocation} for the dirt header separator texture to replace.
         */
        public ResourceLocation getHeaderSeparator() {
            return this.headerSeparator;
        }

        /**
         * @return The {@link ResourceLocation} for the dirt footer separator texture to replace.
         */
        public ResourceLocation getFooterSeparator() {
            return this.footerSeparator;
        }

        /**
         * @return The {@link ResourceLocation} for the dirt tab button texture to replace.
         */
        public WidgetSprites getTabButton() {
            return this.tabButton;
        }
    }
}
