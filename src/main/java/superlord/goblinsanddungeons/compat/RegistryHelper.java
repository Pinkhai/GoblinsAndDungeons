package superlord.goblinsanddungeons.compat;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHelper {
	
	@SuppressWarnings("unused")
	private final String modId;
	private final DeferredRegister<Item> itemRegister;
	private final DeferredRegister<Block> blockRegister;
	
	public RegistryHelper(String modId) {
		this.modId = modId;
		this.itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, modId);
		this.blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
	}
	
	public DeferredRegister<Item> getDeferredItemRegister() {
		return this.itemRegister;
	}
	
	public DeferredRegister<Block> getDeferredBlockRegister() {
		return this.blockRegister;
	}
	
	public <B extends Block> RegistryObject<B> createCompatBlock(String modId, String name, Supplier<? extends B> supplier, @Nullable ItemGroup group) {
		ItemGroup determineGroup = ModList.get().isLoaded(modId) || modId == "indev" ? group : null;
		RegistryObject<B> block = this.blockRegister.register(name, supplier);
		this.itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties().group(determineGroup)));
		return block;
	}

}
