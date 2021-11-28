package com.craftmend.openaudiomc.spigot.modules.predictive.sorage;

import com.craftmend.openaudiomc.generic.database.internal.StoredData;
import com.craftmend.openaudiomc.spigot.modules.predictive.serialization.SerializedAudioChunk;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoredChunkMap extends StoredData {

    private SerializedAudioChunk.ChunkMap chunkMap;

}
