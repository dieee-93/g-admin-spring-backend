package diego.soro.graphql.generated.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.graphql.generated.Generated
public class CATEGORY_GQL {
  private String id;

  private String name;

  private String type;

  private CATEGORY_GQL _parent;

  private List<CATEGORY_GQL> children;

  private List<PRODUCT_GQL> products;

  private List<RAW_MATERIAL_GQL> rawMaterials;

  public CATEGORY_GQL() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CATEGORY_GQL getParent() {
    return _parent;
  }

  public void setParent(CATEGORY_GQL _parent) {
    this._parent = _parent;
  }

  public List<CATEGORY_GQL> getChildren() {
    return children;
  }

  public void setChildren(List<CATEGORY_GQL> children) {
    this.children = children;
  }

  public List<PRODUCT_GQL> getProducts() {
    return products;
  }

  public void setProducts(List<PRODUCT_GQL> products) {
    this.products = products;
  }

  public List<RAW_MATERIAL_GQL> getRawMaterials() {
    return rawMaterials;
  }

  public void setRawMaterials(List<RAW_MATERIAL_GQL> rawMaterials) {
    this.rawMaterials = rawMaterials;
  }

  @Override
  public String toString() {
    return "CATEGORY_GQL{id='" + id + "', name='" + name + "', type='" + type + "', parent='" + _parent + "', children='" + children + "', products='" + products + "', rawMaterials='" + rawMaterials + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CATEGORY_GQL that = (CATEGORY_GQL) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(type, that.type) &&
        Objects.equals(_parent, that._parent) &&
        Objects.equals(children, that.children) &&
        Objects.equals(products, that.products) &&
        Objects.equals(rawMaterials, that.rawMaterials);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, _parent, children, products, rawMaterials);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @diego.soro.graphql.generated.Generated
  public static class Builder {
    private String id;

    private String name;

    private String type;

    private CATEGORY_GQL _parent;

    private List<CATEGORY_GQL> children;

    private List<PRODUCT_GQL> products;

    private List<RAW_MATERIAL_GQL> rawMaterials;

    public CATEGORY_GQL build() {
      CATEGORY_GQL result = new CATEGORY_GQL();
      result.id = this.id;
      result.name = this.name;
      result.type = this.type;
      result._parent = this._parent;
      result.children = this.children;
      result.products = this.products;
      result.rawMaterials = this.rawMaterials;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder type(String type) {
      this.type = type;
      return this;
    }

    public Builder _parent(CATEGORY_GQL _parent) {
      this._parent = _parent;
      return this;
    }

    public Builder children(List<CATEGORY_GQL> children) {
      this.children = children;
      return this;
    }

    public Builder products(List<PRODUCT_GQL> products) {
      this.products = products;
      return this;
    }

    public Builder rawMaterials(List<RAW_MATERIAL_GQL> rawMaterials) {
      this.rawMaterials = rawMaterials;
      return this;
    }
  }
}
