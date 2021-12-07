$fname = "auth.txt";
open(IN, "$fname");
$lines = "";
while (<IN>) {
  chomp;
  @cols = split(/\t/);
  $loginId = $cols[2];
  $loginId =~ s/\s//;
  $loginId =~ s/(\w+)/\L$1/;
  $loginId = substr($loginId, 0, 5);
  $lines .= "$loginId\n";
  print STDOUT "$loginId\n";
}
close(IN);
open(OUT, ">auth.dat");
print OUT $lines;
close(OUT);