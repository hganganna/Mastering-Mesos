/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.aurora.common.args.parsers;

import java.lang.reflect.Type;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;

import org.apache.aurora.common.args.ArgParser;
import org.apache.aurora.common.args.Parser;
import org.apache.aurora.common.args.ParserOracle;
import org.apache.aurora.common.args.Parsers;
import org.apache.aurora.common.collections.Pair;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Pair parser.
 *
 * @author William Farner
 */
@ArgParser
public class PairParser extends TypeParameterizedParser<Pair<?, ?>> {

  public PairParser() {
    super(2);
  }

  @Override
  Pair<?, ?> doParse(ParserOracle parserOracle, String raw, List<Type> typeParams) {
    Type leftType = typeParams.get(0);
    Parser<?> leftParser = parserOracle.get(TypeToken.of(leftType));

    Type rightType = typeParams.get(1);
    Parser<?> rightParser = parserOracle.get(TypeToken.of(rightType));

    List<String> parts = ImmutableList.copyOf(Parsers.MULTI_VALUE_SPLITTER.split(raw));
    checkArgument(parts.size() == 2,
        "Only two values may be specified for a pair, you gave " + parts.size());

    return Pair.of(leftParser.parse(parserOracle, leftType, parts.get(0)),
                   rightParser.parse(parserOracle, rightType, parts.get(1)));
  }
}
