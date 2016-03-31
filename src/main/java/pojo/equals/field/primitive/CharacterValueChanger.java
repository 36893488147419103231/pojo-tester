package pojo.equals.field.primitive;

class CharacterValueChanger extends PrimitiveValueChanger<Character> {

    @Override
    public boolean areDifferentValues(final Character sourceValue, final Character targetValue) {
        return sourceValue.charValue() != targetValue.charValue();
    }

    @Override
    protected Character increaseValue(final Character value) {
        return (char) (value + 1);
    }
}
