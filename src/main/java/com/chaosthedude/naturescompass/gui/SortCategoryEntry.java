package com.chaosthedude.naturescompass.gui;

import com.chaosthedude.naturescompass.sorting.ISortingCategory;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.widget.list.ExtendedList.AbstractListEntry;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SortCategoryEntry extends AbstractListEntry<SortCategoryEntry> {

	private final Minecraft mc;
	private final SortCategoryList sortCategoryList;
	private final ISortingCategory category;

	public SortCategoryEntry(SortCategoryList sortCategoryList, ISortingCategory category) {
		this.category = category;
		this.sortCategoryList = sortCategoryList;
		mc = Minecraft.getInstance();
	}

	@Override
	public void render(MatrixStack matrixStack, int par1, int par2, int par3, int par4, int par5, int par6, int par7, boolean par8, float par9) {
		String name = category.getLocalizedName();
		mc.fontRenderer.func_243248_b(matrixStack, new StringTextComponent(name), par3 + 1, par2 + 1, 0x808080);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			sortCategoryList.selectCategory(this);
			mc.getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		}
		return false;
	}
	
	public final ISortingCategory getCategory() {
		return category;
	}

}
