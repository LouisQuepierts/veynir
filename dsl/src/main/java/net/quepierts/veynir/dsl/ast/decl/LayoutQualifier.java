package net.quepierts.veynir.dsl.ast.decl;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class LayoutQualifier {

    private final Object2IntMap<String> qualifiers;

}
