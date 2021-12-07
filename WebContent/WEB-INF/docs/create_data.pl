$fname = "samples.dat";
open(IN, "$fname");
%hash = ();
while (<IN>) {
  if ($_ !~ /ÅE/) {next;}
  chomp;
  @cols = split(/\t/);
  $hash{$cols[1]} = $cols[1];
}
@values = sort keys %hash;
open(OUT, ">curries.txt");
@cols = ();
%forward = ();
%backward = ();
while ($value = shift(@values)) {
  @cols = split(/ÅE/, $value);
  $forward{$cols[0]} = $cols[0];
  $backward{$cols[1]} = $cols[1];
  print OUT "$value\n";
  print STDOUT "$value\n";
}
print OUT "\n";
@cols = ();
@cols = sort keys %forward;
while ($value = shift(@cols)) {
  print OUT "$value\n";
  print STDOUT "$value\n";
}
print OUT "\n";
@cols = ();
@cols = sort keys %backward;
while ($value = shift(@cols)) {
  print OUT "$value\n";
  print STDOUT "$value\n";
}

close(OUT);
