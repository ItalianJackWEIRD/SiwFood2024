package it.uniroma3.siw.siwfood.model;

import java.util.Arrays;
import java.util.Base64;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Images {

    /* ATTRIBUTI */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private byte[] imageData;

    private String fileName;
    /* FINE ATTRIBUTI */

    /* HASHCODE & EQUALS */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(imageData);
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Images other = (Images) obj;
        if (!Arrays.equals(imageData, other.imageData))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        return true;
    }
    /* FINE HASHCODE & EQUALS */

    /* METODI PER LE FOTO */
    public String getFormat() {
        return getFileName().substring(1 + getFileName().lastIndexOf('.'));
    }

    public String getBase64() {
        return Base64.getEncoder().encodeToString(this.imageData);
    }

    /* GETTER & SETTER */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /* FINE GETTER & SETTER */

}
