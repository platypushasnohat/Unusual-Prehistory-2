package com.unusualmodding.unusual_prehistory.client.book;

import com.google.gson.*;
import com.unusualmodding.unusual_prehistory.client.book.data.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class BookPage {

    public static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(BookPage.class, new Deserializer()).create();

    public String translatableTitle = null;
    private String parent = "";
    private String textFileToReadFrom = "";
    private List<LinkData> linkedButtons = new ArrayList<>();
    private List<EntityLinkData> linkedEntites = new ArrayList<>();
    private List<ItemRenderData> itemRenders = new ArrayList<>();
    private List<RecipeData> recipes = new ArrayList<>();
    private List<EntityRenderData> entityRenders = new ArrayList<>();
    private List<ImageData> images = new ArrayList<>();
    private final String title;

    public BookPage(String parent, String textFileToReadFrom, List<LinkData> linkedButtons, List<EntityLinkData> linkedEntities, List<ItemRenderData> itemRenders, List<RecipeData> recipes, List<EntityRenderData> entityRenders, List<ImageData> images, String title) {
        this.parent = parent;
        this.textFileToReadFrom = textFileToReadFrom;
        this.linkedButtons = linkedButtons;
        this.itemRenders = itemRenders;
        this.linkedEntites = linkedEntities;
        this.recipes = recipes;
        this.entityRenders = entityRenders;
        this.images = images;
        this.title = title;
    }

    public static BookPage deserialize(Reader readerIn) {
        return GsonHelper.fromJson(GSON, readerIn, BookPage.class);
    }

    public static BookPage deserialize(String jsonString) {
        return deserialize(new StringReader(jsonString));
    }

    public String getParent() {
        return parent;
    }

    public String getTitle() {
        return title;
    }

    public String getTextFileToReadFrom() {
        return textFileToReadFrom;
    }

    public List<LinkData> getLinkedButtons() {
        return linkedButtons;
    }

    public List<EntityLinkData> getLinkedEntities() {
        return linkedEntites;
    }

    public List<ItemRenderData> getItemRenders() {
        return itemRenders;
    }

    public List<RecipeData> getRecipes() {
        return recipes;
    }

    public List<EntityRenderData> getEntityRenders() {
        return entityRenders;
    }

    public List<ImageData> getImages() {
        return images;
    }

    public String generateTitle() {
        if (translatableTitle != null) {
            return I18n.get(translatableTitle);
        }
        return title;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Deserializer implements JsonDeserializer<BookPage> {

        public BookPage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext deserializationContext) throws JsonParseException {
            JsonObject jsonobject = GsonHelper.convertToJsonObject(jsonElement, "book page");
            LinkData[] linkedPageRead = GsonHelper.getAsObject(jsonobject, "linked_page_buttons", new LinkData[0], deserializationContext, LinkData[].class);
            EntityLinkData[] linkedEntitesRead = GsonHelper.getAsObject(jsonobject, "entity_buttons", new EntityLinkData[0], deserializationContext, EntityLinkData[].class);
            ItemRenderData[] itemRendersRead = GsonHelper.getAsObject(jsonobject, "item_renders", new ItemRenderData[0], deserializationContext, ItemRenderData[].class);
            RecipeData[] recipesRead = GsonHelper.getAsObject(jsonobject, "recipes", new RecipeData[0], deserializationContext, RecipeData[].class);
            EntityRenderData[] entityRendersRead = GsonHelper.getAsObject(jsonobject, "entity_renders", new EntityRenderData[0], deserializationContext, EntityRenderData[].class);
            ImageData[] imagesRead = GsonHelper.getAsObject(jsonobject, "images", new ImageData[0], deserializationContext, ImageData[].class);

            String readParent = "";
            if (jsonobject.has("parent")) {
                readParent = GsonHelper.getAsString(jsonobject, "parent");
            }

            String readTextFile = "";
            if (jsonobject.has("text")) {
                readTextFile = GsonHelper.getAsString(jsonobject, "text");
            }

            String title = "";
            if (jsonobject.has("title")) {
                title = GsonHelper.getAsString(jsonobject, "title");
            }


            BookPage page = new BookPage(readParent, readTextFile, Arrays.asList(linkedPageRead), Arrays.asList(linkedEntitesRead), Arrays.asList(itemRendersRead), Arrays.asList(recipesRead), Arrays.asList(entityRendersRead), Arrays.asList(imagesRead), title);
            if (jsonobject.has("title")) {
                page.translatableTitle = GsonHelper.getAsString(jsonobject, "title");
            }
            return page;
        }
    }
}
