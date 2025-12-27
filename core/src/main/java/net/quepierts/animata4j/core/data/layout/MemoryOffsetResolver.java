package net.quepierts.animata4j.core.data.layout;

public interface MemoryOffsetResolver {

    /**
     * Get the offset of the element<br>
     * If found the element, return the offset<br>
     * If not found, return -1<br>
     * @param path the path of the element
     * @return the offset, -1 if not found
     */
    int getOffset(String path);

}
