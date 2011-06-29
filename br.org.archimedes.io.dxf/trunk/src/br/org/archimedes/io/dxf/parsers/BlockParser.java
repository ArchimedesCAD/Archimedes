package br.org.archimedes.io.dxf.parsers;

import java.util.Iterator;

import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;

public class BlockParser {
	
	DXFLayer layerWithoutBlocks = new DXFLayer();
	
	public DXFLayer createLayerWithoutBlocksFrom(DXFBlock dxfBlock) {
		
		if(dxfBlock != null) {
	  		Iterator entitiesIterator = dxfBlock.getDXFEntitiesIterator();
	  		
	  		while (entitiesIterator.hasNext()) {
				DXFEntity entity = (DXFEntity) entitiesIterator.next();
				
				if(!entity.isBlockEntity()) {
					layerWithoutBlocks.addDXFEntity(entity);
				}
	  		}
		}
		return layerWithoutBlocks;
	}

}
