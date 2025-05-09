package diego.soro.g-admin.generated.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Generated;
import java.lang.String;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@diego.soro.g-admin.generated.Generated
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "__typename"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CommensurableRawMaterial.class, name = "CommensurableRawMaterial"),
    @JsonSubTypes.Type(value = AccountingRawMaterial.class, name = "AccountingRawMaterial"),
    @JsonSubTypes.Type(value = ProcessedRawMaterial.class, name = "ProcessedRawMaterial")
})
public interface RawMaterial {
  String getId();

  String getName();

  int getCategoryId();

  String getRawMaterialType();

  double getCost();

  double getQuantity();
}
